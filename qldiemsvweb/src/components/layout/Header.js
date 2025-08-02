import { Container, Nav, Navbar, NavDropdown } from "react-bootstrap";
import { Link } from "react-router-dom";

const Header = () => {
    return (
        <>
            <Navbar expand="lg" style={{backgroundColor: '#1c5ebe'}}>
                <Container>
                    <Navbar.Brand href="#home" className="d-flex align-items-center text-white">
                       <img src="/Logo.png" alt="Logo" width="350" height="" className="d-inline-block align-top me-2"
                    /> QUẢN LÝ ĐIỂM SINH VIÊN</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <Navbar.Collapse id="basic-navbar-nav">
                        <Nav className="me-auto">
                            <Link to="/login" className="nav-link text-info">Đăng nhập</Link>
                            <Link to="/register" className="nav-link text-info">Đăng ký</Link>
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        </>
    );
}

export default Header;