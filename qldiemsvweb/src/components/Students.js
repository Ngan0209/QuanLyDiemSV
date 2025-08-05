import { useContext, useEffect, useState } from "react"
import { MyUserContext } from "../configs/Contexts";
import { Link, useNavigate, useParams } from "react-router-dom";
import Api, { authApis, buildUrl, endpoint } from "../configs/Api";
import { Button, Form, Table } from "react-bootstrap";
import cookie from 'react-cookies'

const ListStudent = () => {
    const [studentClasses, setStudentClasses] = useState([]);
    const [user] = useContext(MyUserContext);
    const { classId } = useParams();
    const [q, setQ] = useState();

    const loadStudentClasses = async () => {

        let url = buildUrl(endpoint.liststudent, { classId });

        if (q)
            url = `${url}?kwStudent=${q}`;

        try {
            let res = await authApis().get(url);
            setStudentClasses(res.data);
            console.info(res.data)

        } catch (ex) {
            console.error(ex);
        }
    }

    const deleteColumn = async (classId, typeGradeName) => {
        const confirmDelete = window.confirm(`Bạn có chắc chắn muốn xóa cột điểm "${typeGradeName}" không?`);
        if (!confirmDelete) return;

        try {
            let url = buildUrl(endpoint.delete, { classId });
            await Api.delete(url, {
                params: { name: typeGradeName }
            });

            alert("Xóa thành công!");
        } catch (ex) {
            console.error(ex);
            alert("Xóa thất bại!")
        } finally {
            loadStudentClasses();
        }
    }

    useEffect(() => {
        let timer = setTimeout(() => {
            loadStudentClasses();
        }, 500);

        return () => clearTimeout(timer);
    }, [user, classId, q])

    return (
        <>
            <div className="container mt-5">
                <h2 className="mb-4">Danh sách sinh viên</h2>
                <Form>
                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                        <Form.Control value={q} onChange={e => setQ(e.target.value)} type="text" placeholder="Tìm kiếm sinh viên..." />
                    </Form.Group>
                </Form>
                <Link to={""} variant="success" className="btn btn-danger btn-lg mt-1 mb-3">Nhập điểm</Link>
                <Link to={`/secure/teacher/classes/${classId}/add-GradeColumn`} variant="success" className="btn btn-danger btn-lg mt-1 mb-3 mx-3">Thêm cột điểm</Link>
                {studentClasses.length === 0 ? (
                    <li className="list-group-item">Không có sinh viên nào</li>
                ) : <> <div className="d-flex flex-wrap gap-3 justify-content-start w-100 mb-3">
                    <Table striped bordered hover>
                        <tr>
                            <th>Id</th>
                            <th>Mã số sinh viên</th>
                            <th>Họ và tên</th>
                            <th>Cuối kì</th>
                            <th>Giữa kì</th>
                            {studentClasses[0].grade.typegradeSet !== null && (
                                studentClasses[0].grade.typegradeSet.map(tg =>
                                    <th key={tg.id}>
                                        {tg.name}
                                        <Button onClick={() => deleteColumn(classId, tg.name)} variant="danger" className="p-0 rounded-circle mx-2" style={{ width: '24px', height: '24px' }}>&times;</Button>
                                    </th>
                                )
                            )}
                            <th>Điểm TB</th>
                            <th></th>
                        </tr>

                        <tbody>
                            {studentClasses.map(c => <tr key={c.id}>
                                <td>{c.studentId.id}</td>
                                <td>{c.studentId.studentCode}</td>
                                <td>{c.studentId.name}</td>
                                <td>{c.grade.finalExem}</td>
                                <td>{c.grade.midterm}</td>
                                {c.grade.typegradeSet !== null && (
                                    c.grade.typegradeSet.map(tg => (
                                        tg.grade !== null ?
                                            <td key={tg.id}>{tg.grade}</td>
                                            : <td key={tg.id}>--</td>
                                    ))
                                )}
                                {c.grade.averageScore != null ? 
                                <td>{c.grade.averageScore}</td> : 
                                <td>--</td>}

                                <td>
                                    <Link to={`/secure/teacher/students/${c.studentId.id}`} variant="success" className="btn btn-primary btn-sm">Xem thêm</Link>
                                </td>
                            </tr>)}

                        </tbody>
                    </Table>

                </div>
                </>}

            </div >
        </>
    );
}

export default ListStudent;