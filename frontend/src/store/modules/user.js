export const user = {
  state: () => ({
    accessToken: null
  }),
  getters: {
    accessToken: (state) => state.accessToken
  },
}
