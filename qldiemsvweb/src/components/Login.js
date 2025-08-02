import { useRef, useState } from "react";
import { Button, Col, Container, Row } from "react-bootstrap";
import Form from 'react-bootstrap/Form';

const Login = () => {
    const info = [{
        "title": "Tên đăng nhập",
        "field": "username",
        "type": "text"
    }, {
        "title": "Mật khẩu",
        "field": "passworld",
        "type": "password"
    }];

    const [user, setUser] = useState({});


    const login = (event) => {
        event.prevenDefault();
    }

    return (
        <>
            <h1 className="text-center text-success mt-2">ĐĂNG NHẬP</h1>
            <Container>
                <Row className="justify-content-center">
                    <Col xs={18} sm={16} md={10} lg={8}>
                        <Form onSubmit={login}>
                            {info.map(i => <Form.Group key={i.field} className="mb-3" controlId={i.field}>
                                <Form.Label>{i.title}</Form.Label>
                                <Form.Control type={i.type} placeholder={i.title} />
                            </Form.Group>)}

                            <Form.Group className="mb-3" controlId="exampleForm.ControlInput2">
                                <Button type="submit" variant="success">Đăng nhập</Button>
                            </Form.Group>
                        </Form >
                    </Col>
                </Row>
            </Container>
        </>
    )
}

export default Login;