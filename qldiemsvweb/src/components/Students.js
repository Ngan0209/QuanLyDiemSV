import { useContext, useEffect, useState } from "react"
import { MyUserContext } from "../configs/Contexts";
import { Link, useParams } from "react-router-dom";
import Api, { authApis, buildUrl, endpoint } from "../configs/Api";
import { Button, Form, Table } from "react-bootstrap";
import jsPDF from "jspdf";
import autoTable from "jspdf-autotable";
import "../libs/RobotoFont";
import { font } from "../libs/RobotoFont";


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

    const exportPDF = () => {
        const doc = new jsPDF();
        doc.addFileToVFS("Roboto-Regular-normal.ttf", font);
        doc.addFont("Roboto-Regular-normal.ttf", "Roboto-Regular", "normal");
        doc.setFont("Roboto-Regular");
        doc.setFontSize(14);
        doc.text("Danh sách điểm sinh viên", 14, 16);

        const c = studentClasses[0]?.classId;
        doc.setFontSize(12);
        doc.text(`Lớp: ${c.name}`, 14, 26);
        doc.text(`Môn học: ${c.courseId.name}`, 14, 32);
        doc.text(`Giảng viên: ${c.teacherId.name}`, 14, 38);

        const headers = ["MSSV", "Ho ten", "Giua ky", "Cuoi ky"];
        const firstRow = studentClasses[0];
        const extraColumns = firstRow.grade.typegradeSet?.map(tg => tg.name) || [];
        headers.push(...extraColumns, "Điem TB");

        const rows = studentClasses.map(c => {
            const row = [
                c.studentId.studentCode,
                c.studentId.name,
                c.grade.midterm,
                c.grade.finalExem,
            ];

            if (c.grade.typegradeSet) {
                c.grade.typegradeSet.forEach(tg => {
                    row.push(tg.grade !== null ? tg.grade : "--");
                });
            }

            row.push(c.grade.averageScore !== null ? c.grade.averageScore : "--");
            return row;
        });

        autoTable(doc, {
            startY: 44,
            head: [headers],
            body: rows,
        });

        doc.save(`Diem_Lop_${c.name}.pdf`);
    }

    const exportCSV = () => {
        const headers = ["MSSV", "Họ tên", "Giữa kỳ", "Cuối kỳ"];
        const firstRow = studentClasses[0];
        const extraColumns = firstRow?.grade?.typegradeSet?.map(tg => tg.name) || [];
        headers.push(...extraColumns, "Điểm TB");

        const c = studentClasses[0].classId;

        const metaInfo = [
            `Lớp: ${c.name || "--"}`,
            `Môn học: ${c.courseId.name || "--"}`,
            `Giảng viên: ${c.teacherId.name || "--"}`,
            "" 
        ];

        const rows = studentClasses.map(c => {
            const row = [
                c.studentId.studentCode,
                c.studentId.name,
                c.grade.midterm,
                c.grade.finalExem,
            ];

            if (c.grade.typegradeSet) {
                c.grade.typegradeSet.forEach(tg => {
                    row.push(tg.grade !== null ? tg.grade : "--");
                });
            }

            row.push(c.grade.averageScore !== null ? c.grade.averageScore : "--");
            return row.join(",");
        });

        const csvContent = [...metaInfo, headers.join(","), ...rows].join("\n");
        const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
        const url = URL.createObjectURL(blob);

        const a = document.createElement("a");
        a.href = url;
        a.download = `Diem_Lop_${c.name}.csv`;
        a.click();
    };


    return (
        <>
            <div className="container mt-5">
                <h2 className="mb-4">Danh sách sinh viên</h2>
                <Form>
                    <Form.Group className="mb-3" controlId="exampleForm.ControlInput1">
                        <Form.Control value={q} onChange={e => setQ(e.target.value)} type="text" placeholder="Tìm kiếm sinh viên..." />
                    </Form.Group>
                </Form>
                <Link to={`/secure/teacher/classes/${classId}/saveGrade`} variant="success" className="btn btn-danger btn-lg mt-1 mb-3">Nhập điểm</Link>
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

                <Button onClick={exportPDF} variant="primary" className="btn btn-lg mt-1 mb-3 mx-2">Xuất PDF</Button>
                <Button onClick={exportCSV} variant="warning" className="btn btn-lg mt-1 mb-3 mx-2">Xuất CSV</Button>
            </div >
        </>
    );
}

export default ListStudent;