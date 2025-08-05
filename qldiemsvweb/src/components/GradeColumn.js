import { useState } from "react";
import {useNavigate, useParams } from "react-router-dom";
import { Button, Col, Container, Row, Form } from "react-bootstrap";
import { authApis, buildUrl, endpoint } from "../configs/Api";
import MySpinner from "./layout/MySpinner";

const GradeColumn = () => {
    const [columnName, setColumnName] = useState('');
    const { classId } = useParams();
    const [loading, setLoading] = useState(false);
    const nav = useNavigate();

    const addColumn = async (e) => {
        e.preventDefault();

        try {
            setLoading(true);
            let url = buildUrl(endpoint.gradecolumn, { classId });
            let res = await authApis().post(url, {
                columnName :  columnName 
            });

            if (res.status === 200 || res.status === 201)
                nav(`/secure/teacher/classes/${classId}/students`)
        } catch (ex) {
            console.error(ex);
        } finally {
            setLoading(false);
        }

    };

    return (
        <>
            <h3 className="text-center text-info mt-4">Thêm Cột Điểm</h3>
            <Container>
                <Row className="justify-content-center">
                    <Col xs={12} sm={10} md={8} lg={6}>
                        <Form onSubmit={addColumn}>
                            <Form.Group className="mb-3" controlId="columnName">
                                <Form.Label>Tên cột điểm</Form.Label>
                                <Form.Control type="text" placeholder="VD: Thực hành" value={columnName} onChange={(e) => setColumnName(e.target.value)} required disabled={loading} />
                            </Form.Group>
                            {loading ? <MySpinner /> : <Form.Group className="mb-3" controlId="exampleForm.ControlInput2">
                                <Button type="submit" variant="success">Lưu</Button>
                            </Form.Group>}
                        </Form>
                    </Col>
                </Row>
            </Container>
        </>
    );
};
export default GradeColumn;