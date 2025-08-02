import axios from "axios";

const BASE_URL = 'http://localhost:8080/QuanLyDiemSV/api/';

export const endpoint = {
    'semesters':'/semesters',
    'register':'/users',
    'login':'/login'
}

export default axios.create({
    baseURL: BASE_URL
})