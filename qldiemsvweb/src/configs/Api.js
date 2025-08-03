import axios from "axios";
import cookie from 'react-cookies'

const BASE_URL = 'http://localhost:8080/QuanLyDiemSV/api/';

export const endpoint = {
    'semesters':'/semesters',
    'register':'/users',
    'login':'/login',
    'profile': '/secure/profile',
    'classes': '/secure/semesters/{semesterId}/classes',
    'grades': '/secure/student/semesters/{semesterId}/classes/grades'
}

export const buildUrl = (template, params) => {
    let url = template;
    for (let key in params) {
        url = url.replace(`{${key}}`, params[key]);
    }
    return url;
};

export const authApis = () => axios.create({
    baseURL: BASE_URL,
    headers: {
        'Authorization': `Bearer ${cookie.load('token')}`
    }
})

export default axios.create({
    baseURL: BASE_URL
})