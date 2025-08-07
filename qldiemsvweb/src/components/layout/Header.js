import { useContext } from "react";
import { Button, Container, Nav, Navbar} from "react-bootstrap";
import { Link } from "react-router-dom";
import { MyUserContext } from "../../configs/Contexts";

const Header = () => {
    const [user, dispatch] = useContext(MyUserContext);

    return (
        <>
            <Navbar expand="lg" style={{ backgroundColor: '#1c5ebe' }}>
                <Container>
                    <Navbar.Brand href="#home" className="d-flex align-items-center text-white">
                        <img src="/Logo.png" alt="Logo" width="350" height="" className="d-inline-block align-top me-2"
                        /> QUẢN LÝ ĐIỂM SINH VIÊN</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto">
                            <Link to="/" className="nav-link text-info">Trang chủ </Link>
                            {user === null ? <>
                                <Link to="/register" className="nav-link text-info">Đăng ký</Link>
                                <Link to="/login" className="nav-link text-info">Đăng nhập</Link>
                            </> : <>

                                <Link to="/" className="nav-link text-info">
                                    <img src={user.avatar} width={30} className="rounded" />
                                    Chào {user.username}!
                                </Link>
                                <Button variant="danger" onClick={() => dispatch({ "type": "logout" })}>Đăng xuất</Button>
                            </>}
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </>
    );
}

export default Header;