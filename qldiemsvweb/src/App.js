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
import cookie from 'react-cookies';
import { authApis, endpoint } from './configs/Api';
import SemesterClasses from './components/SemesterClasses';
import ViewGrades from './components/ViewGrade';

function App() {
  let [user, dispatch] = useReducer(MyUserReducer, null);

  // const loadUser = async () => {
  //     const token = cookie.load("token");
  //     if (token) {
  //       try {
  //         const res = await authApis().get(endpoint['profile']);
  //         dispatch({
  //           type: "login",
  //           payload: res.data
  //         });
  //       } catch (err) {
  //         console.error("Không thể tự động đăng nhập lại:", err);
  //         cookie.remove("token");
  //       }
  //     }
  //   };

    // useEffect(() => {
    //   loadUser();
    // },[]);

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
              <Route path='/secure/semesters/:semesterId/classes' element={<SemesterClasses />} />
              <Route path='/secure/student/semesters/:semesterId/classes/grades' element={<ViewGrades />}/>
            </Routes>
          </Container>

          <Footer />
        </BrowserRouter>
      </MyUserContext.Provider>
    </div>
  );
}

export default App;
