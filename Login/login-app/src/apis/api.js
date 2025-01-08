import axios from 'axios'

// 기본 URL 설정
axios.defaults.baseURL = "/api"

// axios 객체 생성
const api = axios.create()


export default api