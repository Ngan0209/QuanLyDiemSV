import { useContext, useEffect, useState } from "react"
import { MyUserContext } from "../configs/Contexts";
import { useParams } from "react-router-dom";
import Api, { authApis, buildUrl, endpoint } from "../configs/Api";
const ViewGrades = () => {
    const [grades, setGrades] = useState([]);
    const [user] = useContext(MyUserContext);
    const { semesterId } = useParams();

    const loadGrades = async () => {
        try {
            const url = buildUrl(endpoint.grades, { semesterId });
            const res = await authApis().get(url);
            console.log("API response:", res.data);
            setGrades(res.data);

        } catch (ex) {
            console.error("không load được điểm",ex);
        }
    }

    useEffect(() => {
        loadGrades();
    }, [user, semesterId])

    return (
        <div className="container mt-5">
            <h2>Bảng điểm học kỳ {semesterId}</h2>

            {grades.length === 0 ? (
                <p>Không có điểm nào</p>
            ) : (
                <table className="table table-bordered">
                    <thead>
                        <tr>
                            <th>Môn học</th>
                            <th>Giữa kỳ</th>
                            <th>Cuối kỳ</th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {grades.map((g, i) => (
                            <tr key={g.id}>
                                <td>Môn {i+1}</td>
                                
                                <td>{g.midterm}</td>
                                <td>{g.finalExem}</td>
                                <td>
                                    {g.typegradeSet?.length > 0 ? (
                                        <ul>
                                            {g.typegradeSet.map(t => (
                                                <li key={t.id}>{t.name}: {t.grade}</li>
                                            ))}
                                        </ul>
                                    ) : (
                                        "--"
                                    )}
                                </td>
                                
                            </tr>
                        ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default ViewGrades;