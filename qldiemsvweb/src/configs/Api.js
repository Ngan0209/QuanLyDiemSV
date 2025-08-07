import axios from "axios";
import cookie from 'react-cookies'

const BASE_URL = 'http://localhost:8080/QuanLyDiemSV/api/';

export const endpoint = {
    'semesters':'/semesters',
    'register':'/users',
    'login':'/login',
    'profile': '/secure/profile',
    'studentclasses': '/secure/student/semesters/{semesterId}/classes',
    'grades': '/secure/student/semesters/{semesterId}/classes/grades',
    'teacherclasses': '/secure/teacher/semesters/{semesterId}/classes',
    'liststudent': '/secure/teacher/classes/{classId}/students',
    'detailstudent':'/secure/teacher/students/{studentId}',
    'delete': 'delete-columnGrade/classes/{classId}',
    'gradecolumn': '/secure/teacher/classes/{classId}/add-GradeColumn',
    'savegrade': '/secure/teacher/classes/{classId}/saveGrade'

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
    },
   
})

// export const authApis = () => {
//     const token = cookie.load('token');
//     const role = cookie.load('role');
//     console.info("role:", role);
//     console.info("Token:", token);

//     return axios.create({
//         baseURL: BASE_URL,
//         headers: {
//             'Authorization': `Bearer ${token}`
//         }
//     });
// }

export default axios.create({
    baseURL: BASE_URL
})