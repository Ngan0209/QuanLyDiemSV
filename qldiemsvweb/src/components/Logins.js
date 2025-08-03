import { useContext, useState } from "react";
import { Button, Col, Container, Row, Form } from "react-bootstrap";
import Api, { authApis, endpoint } from "../configs/Api";
import MySpinner from "./layout/MySpinner";
import cookie from 'react-cookies';
import { useNavigate, useSearchParams } from "react-router-dom";
import { MyUserContext } from "../configs/Contexts";

const Login = () => {

    const info = [{
        "title": "Tên đăng nhập",
        "field": "username",
        "type": "text"
    }, {
        "title": "Mật khẩu",
        "field": "password",
        "type": "password"
    }];

    const [, dispatch] = useContext(MyUserContext);
    const [loading, setLoading] = useState(false);

    const [user, setUser] = useState({});
    const nav = useNavigate();
    const [q] = useSearchParams();


    const login = async (event) => {
        event.preventDefault();

        try {
            setLoading(true);
            let res = await Api.post(endpoint['login'], {
                ...user
            });

            console.info(res.data);
            cookie.save('token', res.data.token, { path: '/' });

            let u = await authApis().get(endpoint['profile']);
            
            dispatch({
                "type": "login",
                "payload": u.data
            });

            let next = q.get('next');
            nav(next?next:"/");
        } catch (ex) {
            console.error(ex);
        } finally {
            setLoading(false)
        }
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
                                <Form.Control required value={user[i.field]} onChange={e => setUser({ ...user, [i.field]: e.target.value })} type={i.type} placeholder={i.title} />
                            </Form.Group>)}

                            {loading ? <MySpinner /> : <Form.Group className="mb-3" controlId="exampleForm.ControlInput2">
                                <Button type="submit" variant="success">Đăng nhập</Button>
                            </Form.Group>}
                        </Form >
                    </Col>
                </Row>
            </Container>
        </>
    )
}

export default Login;