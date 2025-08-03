import { useRef, useState } from "react";
import { Alert, Button, Col, Container, Row, Form } from "react-bootstrap";
import Api, { endpoint } from "../configs/Api";
import { useNavigate } from "react-router-dom";
import MySpinner from "./layout/MySpinner";

const Register = () => {
    const info = [{
        "title": "Tên",
        "field": "firstName",
        "type": "text"
    }, {
        "title": "Họ và tên lót",
        "field": "lastName",
        "type": "text"
    }, {
        "title": "Email",
        "field": "email",
        "type": "email"
    }, {
        "title": "Mã số sinh viên",
        "field": "studentCode",
        "type": "text"
    }, {
        "title": "Tên đăng nhập",
        "field": "username",
        "type": "text"
    }, {
        "title": "Mật khẩu",
        "field": "password",
        "type": "password"
    }, {
        "title": "Xác nhận mật khẩu",
        "field": "confirm",
        "type": "password"
    }];

    const avatar = useRef();
    const [user, setUser] = useState({});
    const [msg, setMsg] = useState();
    const [loading, setLoading] = useState(false);
    const nav = useNavigate();

    const validate = () => {
        if (user.confirm === null || user.password === null || user.confirm !== user.password)
            setMsg("Mật khẩu KHÔNG khớp!");

        return true;
    }

    const register = async (event) => {
        event.preventDefault();

        if (validate()) {
            try {
                setLoading(true);
                let formData = new FormData();
                for (let key in user)
                    if (key !== 'confirm')
                        formData.append(key, user[key]);

                if (avatar.current.files.length > 0) {
                    formData.append("avatar", avatar.current.files[0]);
                }

                console.info(formData);
                let res = await Api.post(endpoint['register'], formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                });

                if (res.status === 201)
                    nav("/login");
            } catch (ex) {
                console.error(ex);
            } finally {
                setLoading(false);
            }
        }
    }

    return (
        <>
            <h1 className="text-center text-success mt-2">ĐĂNG KÝ</h1>
            <Container>
                <Row className="justify-content-center">
                    <Col xs={18} sm={16} md={10} lg={8}>
                        {msg && <Alert variant="danger">{msg}</Alert>}
                        <Form onSubmit={register}>
                            {info.map(i => <Form.Group key={i.field} className="mb-3" controlId={i.field}>
                                <Form.Label>{i.title}</Form.Label>
                                <Form.Control required value={user[i.field]} onChange={e => setUser({ ...user, [i.field]: e.target.value })} type={i.type} placeholder={i.title} />
                            </Form.Group>)}

                            <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                                <Form.Label>Ảnh đại diện</Form.Label>
                                <Form.Control type="file" ref={avatar} />
                            </Form.Group>

                            {loading ? <MySpinner /> : <Form.Group className="mb-3" controlId="exampleForm.ControlInput2">
                                <Button type="submit" variant="success">Đăng ký</Button>
                            </Form.Group>}
                        </Form>
                    </Col>
                </Row>
            </Container>
        </>
    )
}

export default Register;