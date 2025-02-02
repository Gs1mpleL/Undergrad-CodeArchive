import os
import sys
import time

from lcu_driver import Connector
from pynput.keyboard import Controller
from pynput import keyboard


# 请求器
connector = Connector()
kb = Controller()

# 用户信息
me_data = {}
# 对局中玩家信息
team_list = []
# 英雄对应
hero_dic = {}
global_game_state = ""


def get_hero_name_from_config():
    # 文件路径
    file_path = 'config.txt'
    # 检查文件是否存在
    if not os.path.exists(file_path):
        # 如果文件不存在，创建文件并写入 'a b c'
        with open(file_path, 'w', encoding="utf8") as file:
            file.write('不锁定')
            config_from_txt = '不锁定'
        print("ERROR:"+"【请在同文件夹下config.txt输入你要选择的英雄】")
        time.sleep(5)
        sys.exit(0)
    else:
        # 如果文件存在，读取第一行
        with open(file_path, 'r', encoding="utf8") as file:
            first_line = file.readline()
            config_from_txt = first_line
    return config_from_txt


# 玩家信息类
class PlayerInfo:
    def __init__(self, displayName, heroName, summonerId, level, puuid, accountId, rank, game_history_list,
                 game_history_str, score,
                 team):
        self.game_history_list = game_history_list
        self.send_msg = None
        self.heroName = heroName
        self.score = score
        self.game_history = game_history_str
        self.rank = rank
        self.puuid = puuid
        self.accountId = accountId
        self.summonerId = summonerId
        self.summonerLevel = level
        self.displayName = displayName
        self.team = team
        self.buildStr()

    def buildStr(self):
        self.send_msg = f"{self.team}：【{self.heroName}】-【{self.displayName}】-【{self.rank}】-【{self.game_history}】-【评分：{self.score:.2f}】"

    def print_info(self):
        print(self.send_msg)


class GameScore:
    def __init__(self, kills, deaths, assists, win):
        self.win = win
        self.assists = assists
        self.deaths = deaths
        self.kills = kills

    def print(self):
        print(self.__dict__)


def on_release(key):
    try:
        other_team = team_list[0:5]  # 敌方数据 类型为List<PlayerInfo>
        my_team = team_list[5:11]  # 我方数据 类型为List<PlayerInfo>
        # TODO:无需求目前

    except Exception as e:
        print("键盘请求处理失败")
        print(e)


def start_keyboard_listener():
    with keyboard.Listener(on_release=on_release) as listener:
        listener.join()


# fired when League Client is closed (or disconnected from websocket)
@connector.close
async def disconnect(_):
    print('The client have been closed!')


# 获取登录玩家信息
async def get_summoner_data(connection):
    summoner = await connection.request('GET', '/lol-summoner/v1/current-summoner')
    global me_data
    me_data = await summoner.json()
    print(f"---")
    print(f"用户名:    {me_data['displayName']}")
    print(f"用户等级:   {me_data['summonerLevel']}")
    print(f"summonerId:      {me_data['summonerId']}")
    print(f"puuid:      {me_data['puuid']}")
    print(f"---")


# 接受比赛
async def accept(connection):
    await connection.request('POST', '/lol-matchmaking/v1/ready-check/accept')
    print("AUTO:[已接受匹配]")


# 游戏开始，解析对战玩家信息
async def handle_player_info(connection):
    print("AUTO:[开始解析本局玩家信息]")
    if global_game_state != "InProgress":
        print("AUTO:[未在游戏对局中！]")
        return
    global team_list
    t1_json, t2_json, flag = await get_uuids_gaming(connection)
    t1_player_info_list = []
    t2_player_info_list = []
    for player in t1_json:
        tmp_player = await get_player_info_by_puuid(connection, player["puuid"])
        tmp_player.heroName = hero_dic[player["championId"]]
        if flag:
            tmp_player.team = "我方"
        else:
            tmp_player.team = "敌方"
        tmp_player.buildStr()
        tmp_player.print_info()
        t1_player_info_list.append(tmp_player)
    for player in t2_json:
        tmp_player = await get_player_info_by_puuid(connection, player["puuid"])
        tmp_player.heroName = hero_dic[player["championId"]]
        if not flag:
            tmp_player.team = "我方"
        else:
            tmp_player.team = "敌方"
        tmp_player.buildStr()
        tmp_player.print_info()
        t2_player_info_list.append(tmp_player)
    if not flag:
        for t1_player_info in t1_player_info_list:
            team_list.append(t1_player_info)
        for t2_player_info in t2_player_info_list:
            team_list.append(t2_player_info)
    else:
        for t2_player_info in t2_player_info_list:
            team_list.append(t2_player_info)
        for t1_player_info in t1_player_info_list:
            team_list.append(t1_player_info)


# 创建匹配房间
async def create_lobby(connection):
    print("AUTO:[创建匹配房间]")
    queue = {'queueId': 430}
    await connection.request('POST', '/lol-lobby/v2/lobby', data=queue)


# 查询当前对局所有的puuid
async def get_uuids_gaming(connection):
    session_json = await (await connection.request('GET', '/lol-gameflow/v1/session')).json()
    game_data = session_json["gameData"]
    team_1_json = game_data["teamOne"]
    team_2_json = game_data["teamTwo"]
    flag = False
    if me_data == {}:
        await get_summoner_data(connection)
    for player in team_1_json:
        if me_data["puuid"] in player["puuid"]:
            flag = True
    return team_1_json, team_2_json, flag


# 根据puuid查询info
async def get_player_info_by_puuid(connection, puuid, history_count=20):
    history_count -= 1  # 输入20，返回21条数据
    id_json = await (await connection.request('GET', '/lol-summoner/v2/summoners/puuid/' + puuid)).json()
    accountId = id_json["accountId"]
    displayName = id_json["displayName"]
    summonerId = id_json["summonerId"]
    summonerLevel = id_json["summonerLevel"]
    rank = await get_rank_by_puuid(connection, puuid)
    match_history_json = await (await connection.request('GET',
                                                         f"/lol-match-history/v1/products/lol/{puuid}/matches"
                                                         f"?begIndex=0&endIndex={history_count}")).json()
    match_history_json = match_history_json["games"]["games"]
    game_history_list = []
    # 比赛历史解析，评分
    score = 0
    win_count = 0
    game_record_list_5 = []
    for one_game in match_history_json:
        record = one_game["participants"][0]["stats"]
        one_score = GameScore(record["kills"], record["deaths"], record["assists"], record["win"])
        is_win = "胜" if one_score.win else "负"
        game_record_list_5.append(f"({one_score.kills}/{one_score.deaths}/{one_score.assists}/{is_win})")
        if one_score.win:
            score += 1
            win_count += 1
        else:
            score += 0
        score += (one_score.kills * 1.2 + one_score.assists * 0.8) / max(one_score.deaths * 1.2, 1)  # 评分方式
        game_history_list.append(one_score)
    # 最终的用户
    player = PlayerInfo(displayName, "hero_name", summonerId, summonerLevel, puuid, accountId, rank, game_history_list,
                        f"统计:{len(match_history_json)}场 胜:{win_count}场 胜率:{(win_count / len(match_history_json)):.2f}",
                        score, True)
    return player


# 根据puuid获取段位信息
async def get_rank_by_puuid(connection, puuid):
    rank_tier_dic = {
        "IRON": "坚韧黑铁",
        "BRONZE": "英勇黄铜",
        "SILVER": "不屈白银",
        "GOLD": "荣耀黄金",
        "PLATINUM": "华贵铂金",
        "EMERALD": "翡翠",
        "DIAMOND": "璀璨钻石",
        "MASTER": "超凡大师",
        "GRANDMASTER": "傲世宗师",
        "CHALLENGER": "最强王者",
        "UNRANKED": "没有段位",
        "": "无段位"
    }
    queue_dic = {
        "RANKED_SOLO_5x5": "单排/双排",
        "RANKED_FLEX_SR": "灵活组排 5v5",
        "RANKED_FLEX_TT": "灵活组排 3v3",
        "RANKED_TFT": "云顶之弈"
    }
    rank_json = await (await connection.request('GET', '/lol-ranked/v1/ranked-stats/' + puuid)).json()
    highestRankedEntry = rank_json["highestRankedEntry"]
    highestTier = highestRankedEntry["highestTier"]
    if highestTier in rank_tier_dic.keys():
        highestTier = rank_tier_dic[highestTier]
    division = highestRankedEntry["division"]
    queueType = highestRankedEntry["queueType"]
    if queueType in queue_dic.keys():
        queueType = queue_dic[queueType]
    rank_str = f"{highestTier}-{division}({queueType})"
    return rank_str


# 获取英雄字典
async def get_hero_dic(connection):
    hero_res = await connection.request('GET', '/lol-champions/v1/owned-champions-minimal')
    hero_list = await hero_res.json()
    for hero in hero_list:
        hero_dic[hero["id"]] = hero["name"] + "-" + hero["title"]
    print(f"AUTO:[英雄字典准备完成]")


# 根据名称选择英雄
async def select_hero(connection, name):
    try:
        global hero_dic
        print(f"AUTO:[自动选择英雄：{name}]")
        # 获取英雄id
        hero_id = -1
        hero_res = await connection.request('GET', '/lol-champions/v1/owned-champions-minimal')
        hero_list = await hero_res.json()
        for hero in hero_list:
            if hero["name"] == name or hero["title"] == name:
                hero_id = hero["id"]
                break

        if hero_id == -1:
            print("未找到英雄")
            return
        # 获取房间信息
        lobby_res = await connection.request('GET', '/lol-champ-select/v1/session')
        lobby_data = await lobby_res.json()
        myTeam_data = lobby_data["myTeam"]
        actions = lobby_data["actions"][0]
        cellId = -1
        select_param = {}
        select_param_id = -1
        if me_data == {}:
            await get_summoner_data(connection)
        for team_item in myTeam_data:
            if team_item["summonerId"] == me_data["summonerId"]:
                cellId = team_item["cellId"]
        for action in actions:
            if action["actorCellId"] == cellId:
                select_param = action
                select_param_id = action["id"]
        select_param["championId"] = hero_id
        # 选择英雄
        await connection.request('PATCH', f'/lol-champ-select/v1/session/actions/{select_param_id}', data=select_param)
        # 锁定英雄
        await connection.request('POST', f'/lol-champ-select/v1/session/actions/{select_param_id}/complete')
    except IndexError:
        print("AUTO:[自动选择英雄错误，可能目前不是匹配模式]")


async def get_game_state(connection):
    state = await (await connection.request("GET", "/lol-gameflow/v1/gameflow-phase")).json()
    global global_game_state
    global_game_state = state


# 检测游戏状态
@connector.ws.register('/lol-gameflow/v1/gameflow-phase', event_types=('UPDATE',))
async def game_flow(connection, event):
    global global_game_state
    global_game_state = event.data
    global team_list
    if event.data == "None":
        print("GameFlow:[正在主页]")
    elif event.data == "Lobby":
        print("GameFlow:[正在大厅]")
    elif event.data == "Matchmaking":
        print("GameFlow:[开始匹配]")
    elif event.data == "ReadyCheck":
        print("GameFlow:[找到对局]")
        if hero_name != "不锁定":
            await accept(connection)
    elif event.data == "ChampSelect":
        print("GameFlow:[英雄选择]")
        team_list = []
        await get_hero_dic(connection)
        if hero_name != "不锁定":
            await select_hero(connection, hero_name)
    elif event.data == "InProgress":
        print("GameFlow:[游戏中]")
        await handle_player_info(connection)
    elif event.data == "PreEndOfGame":
        print("GameFlow:[游戏即将结束]")
    elif event.data == "WaitingForStats":
        print("GameFlow:[等待结算页面]")
    elif event.data == "EndOfGame":
        print("GameFlow:[游戏结束]")
        team_list = []
    elif event.data == "Reconnect":
        print("GameFlow:[等待重新连接]")


# fired when LCU API is ready to be used
@connector.ready
async def connect(connection):
    # 开启键盘监听 目前无想法
    # threading.Thread(target=start_keyboard_listener).start()
    print(f"配置信息:【锁定英雄名：{hero_name}】")
    await get_game_state(connection)
    # 获取用户信息
    await get_summoner_data(connection)
    await handle_player_info(connection)



hero_name = get_hero_name_from_config()
# 要选择的英雄
connector.start()
# 打包 auto-py-to-exe
