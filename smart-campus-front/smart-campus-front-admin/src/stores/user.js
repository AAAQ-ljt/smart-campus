import { ref } from 'vue'
import { defineStore } from 'pinia'
import { getToken, setToken, removeToken } from '@/utils/token'
import { getAllMenuCodes } from '@/router/menu'
import { logout as logoutApi } from '@/api/login'

export const useUserStore = defineStore('user', () => {
  const token = ref(getToken())
  const userInfo = ref(null)
  const menuCodes = ref(getAllMenuCodes())

  function hasMenu(code) {
    return menuCodes.value.includes(code)
  }

  function setLogin(tokenVal, user) {
    token.value = tokenVal
    userInfo.value = user
    setToken(tokenVal)
  }

  async function logout() {
    try { await logoutApi() } catch {}
    token.value = ''
    userInfo.value = null
    removeToken()
  }

  return { token, userInfo, menuCodes, hasMenu, setLogin, logout }
})
