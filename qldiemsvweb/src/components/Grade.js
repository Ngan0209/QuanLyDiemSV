import { useContext, useEffect, useState } from "react";
import { Alert, Button, Col, Container, Row, Form, Table } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import Api, { authApis, buildUrl, endpoint } from "../configs/Api";
import MySpinner from "./layout/MySpinner";
import { MyUserContext } from "../configs/Contexts";

const AddGrades = () => {
    const { classId } = useParams();
    const [students, setStudents] = useState([]);
    const [user] = useContext(MyUserContext)
    const [msg, setMsg] = useState(null);
    const [loading, setLoading] = useState(false);
    const nav = useNavigate();

    const loadStudentClasses = async () => {

        let url = buildUrl(endpoint.liststudent, { classId });
        try {
            let res = await authApis().get(url);
            setStudents(res.data);
            console.info(res.data)

        } catch (ex) {
            console.error(ex);
        }
    }

    useEffect(() => {
        loadStudentClasses();
    }, [user, classId]);

    const gradeChange = (index, field, value) => {
        const updated = [...students];
        updated[index].grade[field] = value === '' ? null : parseFloat(value);
        setStudents(updated);
    };

    const typegradeChange = (index, tgIndex, value) => {
        const updated = [...students];
        updated[index].grade.typegradeSet[tgIndex].grade = value === '' ? null : parseFloat(value);
        setStudents(updated);
    };

    const validateGrades = () => {
        for (let s of students) {
            if (s.finalExem < 0 || s.finalExem > 10 || s.midterm < 0 || s.midterm > 10)
                return false;
            for (let tg of s.typegradeSet || [])
                if (tg.grade < 0 || tg.grade > 10) return false;
        }
        return true;
    };

    const saveGrades = async (e) => {
        e.preventDefault();
        setMsg(null);
        
        if (!validateGrades()) return;

        const submitData = students.map(s => ({
            id: s.grade.id,
            studentClassId: {
                id: s.id
            },
            finalExem: s.grade.finalExem ,
            midterm: s.grade.midterm ,
            typegradeSet: (s.grade?.typegradeSet ?? []).map(tg => ({
                id: tg.id,
                name: tg.name,
                grade: tg.grade ,
            }))
        }));

        console.info("Payload gửi đi:", submitData);

        try {
            setLoading(true);
            let url = buildUrl(endpoint.savegrade, { classId });
            await authApis().post(url, submitData)

            setMsg("Lưu điểm thành công!");
            nav(`/secure/teacher/classes/${classId}/students`)
        } catch (err) {
            console.error("Lỗi lưu điểm:", err);
            setMsg("Lỗi khi lưu điểm!");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Container className="mt-4">
            <h2 className="text-primary mb-3">Nhập điểm</h2>

            <Row className="justify-content-center">
                <Col lg={12}>
                    {msg && <Alert variant={msg.includes("thành công") ? "success" : "danger"}>{msg}</Alert>}

                    <Form onSubmit={saveGrades}>
                        <Table striped bordered hover>
                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>MSSV</th>
                                    <th>Họ và tên</th>
                                    <th>Cuối kì</th>
                                    <th>Giữa kì</th>
                                    {students.length > 0 && students[0].grade?.typegradeSet && (
                                        students[0].grade.typegradeSet.map(tg =>
                                            <th key={tg.id}>{tg.name}</th>
                                        )
                                    )}
                                </tr>
                            </thead>
                            <tbody>
                                {students.map((s, idx) => (
                                    <tr key={idx}>
                                        <td>{s.studentId.id}</td>
                                        <td>{s.studentId.studentCode}</td>
                                        <td>{s.studentId.name}</td>
                                        <td>
                                            <Form.Control
                                                type="number"
                                                min="0"
                                                max="10"
                                                step="0.01"
                                                value={s.grade.finalExem || ''}
                                                onChange={(e) => gradeChange(idx, "finalExem", e.target.value)}
                                            />
                                        </td>
                                        <td>
                                            <Form.Control
                                                type="number"
                                                min="0"
                                                max="10"
                                                step="0.01"
                                                value={s.grade.midterm || ''}
                                                onChange={(e) => gradeChange(idx, "midterm", e.target.value)}
                                            />
                                        </td>
                                        {s.grade?.typegradeSet?.map((tg, tgIndex) => (
                                            <td key={tg.id}>
                                                <Form.Control
                                                    type="number"
                                                    min="0"
                                                    max="10"
                                                    step="0.01"
                                                    value={tg.grade || ''}
                                                    onChange={(e) => typegradeChange(idx, tgIndex, e.target.value)}
                                                />
                                            </td>
                                        ))}
                                    </tr>
                                ))}

                            </tbody>
                        </Table>

                        {loading ? <MySpinner /> :
                            <div className="text-end">
                                <Button variant="success" type="submit">Lưu điểm</Button>
                            </div>
                        }
                    </Form>
                </Col>
            </Row>
        </Container>
    );
};

export default AddGrades;
