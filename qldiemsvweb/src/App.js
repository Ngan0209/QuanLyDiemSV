import logo from './logo.svg';
import './App.css';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Header from './components/layout/Header';
import Footer from './components/layout/Footer';
import 'bootstrap/dist/css/bootstrap.min.css';
import Home from './components/Home';
import { Container } from 'react-bootstrap';
import Register from './components/Registers';
import Login from './components/Logins';
import MyUserReducer from './reducers/MyUserReducer';
import { useEffect, useReducer } from 'react';
import { MyUserContext } from './configs/Contexts';
import ViewGrades from './components/ViewGrade';
import StudentClasses from './components/StudentClasses';
import TeacherClasses from './components/TeacherClasses';
import ListStudent from './components/Students';
import DetailStudent from './components/DetailStudent';
import GradeColumn from './components/GradeColumn';
import AddGrades from './components/Grade';

function App() {
  let [user, dispatch] = useReducer(MyUserReducer, null);

  return (
    <div style={{
      backgroundImage: 'url("/bg.png")',
      backgroundSize: 'cover',
      backgroundRepeat: 'no-repeat',
      backgroundPosition: 'center center',
      minHeight: '100vh'
    }}>
      <MyUserContext.Provider value={[user, dispatch]}>
        <BrowserRouter>
          <Header />

          <Container>
            <Routes>
              <Route path='/' element={<Home />} />
              <Route path='/login' element={<Login />} />
              <Route path='/register' element={<Register />} />
              <Route path='/secure/student/semesters/:semesterId/classes' element={<StudentClasses />} />
              <Route path='/secure/student/semesters/:semesterId/classes/grades' element={<ViewGrades />}/>
              <Route path='/secure/teacher/semesters/:semesterId/classes' element={<TeacherClasses />} />
              <Route path='/secure/teacher/classes/:classId/students' element={<ListStudent />} />
              <Route path='/secure/teacher/students/:studentId' element={<DetailStudent/>}/>
              <Route path='/secure/teacher/classes/:classId/add-GradeColumn' element={<GradeColumn/>} />
              <Route path='/secure/teacher/classes/:classId/saveGrade' element={<AddGrades/>}/>
            </Routes>
          </Container>

          <Footer />
        </BrowserRouter>
      </MyUserContext.Provider>
    </div>
  );
}

export default App;
