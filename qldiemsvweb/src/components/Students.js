import { useContext, useEffect, useState } from "react"
import { MyUserContext } from "../configs/Contexts";
import { Link, useNavigate, useParams } from "react-router-dom";
import Api, { authApis, buildUrl, endpoint } from "../configs/Api";
import { Button, Form, Table } from "react-bootstrap";
import { FaEye } from "react-icons/fa";

const ListStudent = () => {
    const [students, setStudents] = useState([]);
    const [user] = useContext(MyUserContext);
    const { classId } = useParams();
    const [q, setQ] = useState();

    const loadStudents = async () => {

        let url = buildUrl(endpoint.liststudent, { classId });

        if (q)
            url = `${url}?kwStudent=${q}`;

        try {
            let res = await authApis().get(url);
            setStudents(res.data);
            console.info(res.data)

        } catch (ex) {
            console.error(ex);
        }
    }

    useEffect(() => {
        let timer = setTimeout(() => {
            loadStudents();
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
                <Link to={""} variant="success" className="btn btn-danger btn-lg mt-3 mb-3">Nhập điểm</Link>
                {students.length === 0 ? (
                    <li className="list-group-item">Không có sinh viên nào</li>
                ) : <> <div className="d-flex flex-wrap gap-3 justify-content-start w-100 mb-3">
                    <Table striped bordered hover>
                        <tr>
                            <th>Id</th>
                            <th>Mã số sinh viên</th>
                            <th>Họ và tên</th>
                            <th></th>
                        </tr>

                        <tbody>
                            {students.map(c => <tr key={c.id}>
                                <td>{c.id}</td>
                                <td>{c.studentCode}</td>
                                <td>{c.name}</td>
                                <td>
                                    <Link to={`/secure/teacher/students/${c.id}`} variant="success" className="btn btn-primary btn-sm">Xem thêm</Link>
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