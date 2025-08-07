import { useContext, useEffect, useState } from "react"
import { MyUserContext } from "../configs/Contexts";
import { useNavigate, useParams } from "react-router-dom";
import { authApis, buildUrl, endpoint } from "../configs/Api";
import MySpinner from "./layout/MySpinner";
import { Form, Row, Col, Image } from 'react-bootstrap';

const DetailStudent = () => {
    const [student, setStudent] = useState();
    const [loading, setLoading] = useState(true);
    const [user] = useContext(MyUserContext);
    const nav = useNavigate();
    const { studentId } = useParams();

    const loadStudent = async () => {
        try {
            let url = buildUrl(endpoint.detailstudent, { studentId });
            setLoading(true)
            let res = await authApis().get(url);
            setStudent(res.data);
            console.info(res.data)

        } catch (ex) {
            console.error(ex);
        } finally {
            setLoading(false)
        }
    }

    useEffect(() => {
        loadStudent();
    }, [user, studentId])

    return (
        <>
            {loading ? <MySpinner /> : <>
                
                <h2 className="mt-3"> THÔNG TIN SINH VIÊN</h2>
                        <Row>
                            {/* Hình đại diện */}
                            <Col md={4} className="d-flex align-items-center justify-content-center">
                                <Image
                                    src="/Icon.png"
                                    alt="Avatar"
                                    roundedCircle
                                    style={{ width: '150px', height: '150px', objectFit: 'cover' }}
                                />
                            </Col>

                            {/* Thông tin sinh viên */}
                            <Col md={8}>
                                <Form>
                                    <Form.Group className="mb-2">
                                        <Form.Label>Mã sinh viên</Form.Label>
                                        <Form.Control value={student.studentCode} disabled />
                                    </Form.Group>

                                    <Form.Group className="mb-2">
                                        <Form.Label>Họ và tên</Form.Label>
                                        <Form.Control value={student.name} disabled />
                                    </Form.Group>

                                    <Form.Group className="mb-2">
                                        <Form.Label>Giới tính</Form.Label>
                                        <Form.Control value={student.gender} disabled />
                                    </Form.Group>

                                    <Form.Group className="mb-2">
                                        <Form.Label>Ngày sinh</Form.Label>
                                        <Form.Control value={student.birthday} disabled />
                                    </Form.Group>

                                    <Form.Group className="mb-2">
                                        <Form.Label>Niên khóa</Form.Label>
                                        <Form.Control value={student.schoolYear} disabled />
                                    </Form.Group>

                                    <Form.Group className="mb-2">
                                        <Form.Label>Khoa</Form.Label>
                                        <Form.Control value={student.facultyId.name} disabled />
                                    </Form.Group>
                                </Form>
                            </Col>
                        </Row>
                    
            </>}
        </>
    );
}

export default DetailStudent;