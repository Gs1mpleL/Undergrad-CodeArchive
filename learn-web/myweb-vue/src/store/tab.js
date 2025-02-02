export default {
    state: {
        isCollapse: false,
        menuData: []
    },
    mutations: {
        collapseMenu(state) {
            state.isCollapse = !state.isCollapse
        }
    }
}