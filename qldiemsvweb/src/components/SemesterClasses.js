import { useContext, useEffect, useState } from "react"
import { MyUserContext } from "../configs/Contexts";
import { Link, useNavigate, useParams } from "react-router-dom";
import Api, { authApis, buildUrl, endpoint } from "../configs/Api";
import { Button, Card } from "react-bootstrap";

const SemesterClasses = () => {
    const [classes, setClasses] = useState([]);
    const [user] = useContext(MyUserContext);
    const nav = useNavigate();
    const { semesterId } = useParams();

    const loadClasses = async () => {
        try {
            const url = buildUrl(endpoint.classes, { semesterId });
            const res = await authApis().get(url);
            setClasses(res.data);

        } catch (ex) {
            console.error(ex);
        }
    }

    useEffect(() => {
        if (!user) {
            nav(`/login?next=/secure/semesters/${semesterId}/classes`);
            return;
        }

        loadClasses();
    }, [user, semesterId])

    return (
        <div className="container mt-5">
            <h2>Danh sách lớp học</h2>

            {classes.length === 0 ? (
                <li className="list-group-item">Không có lớp nào</li>
            ) : <> <div className="d-flex flex-wrap gap-3 justify-content-start w-100">
                {classes.map((c, i)=> (
                    <Card key={c.id} style={{ width: '18rem' }} >
                        <Card.Body >
                            <Card.Title>Môn {i+1}: {c.courseId.name}</Card.Title>
                            <Card.Subtitle className="mb-2 text-muted">Lớp: {c.name}</Card.Subtitle>
                            <Card.Subtitle className="mb-2 text-muted">Giảng Viên: {c.teacherId.name}</Card.Subtitle>
                            <Link href="#">Xem thêm</Link>
                            
                        </Card.Body>
                    </Card>
                ))}
            </div>
            </>}
                <Link to={`/secure/student/semesters/${semesterId}/classes/grades`} variant="success" className="btn btn-primary btn-lg px-5 mt-3 mb-3">Xem điểm các môn</Link>
        </div>
    );
}

export default SemesterClasses;