import { useContext, useEffect, useState } from "react";
import Api, { endpoint } from "../configs/Api";
import { Button, Nav, Spinner } from "react-bootstrap";
import { Link, useNavigate } from "react-router-dom";
import MySpinner from "./layout/MySpinner";
import { MyUserContext } from "../configs/Contexts";

const PAGE_SIZE = 3;

const Home = () => {
    const [semesters, setSemesters] = useState([]);
    const [loading, setLoading] = useState(true);
    const [page, setPage] = useState(1);

    const[user] = useContext(MyUserContext);
    const nav = useNavigate();

    const loadSemesters = async () => {
        let url = `${endpoint['semesters']}?page=${page}`;

        try{
            setLoading(true);
            let res = await Api.get(url);
            setSemesters(res.data);
        }catch (ex){
            console.error(ex);
        }finally{
            setLoading(false);
        }
        
    }

    useEffect(() => {
        loadSemesters();
    }, [page]);

    
    return (
        <>

            {loading ? <MySpinner/> : <>
                <div className="d-flex" style={{ marginTop: '200px' }}>
                    <div className="mt-4">
                        <div className="d-flex flex-column gap-3 mb-5">
                            {semesters.map(s => <Link to={`/secure/semesters/${s.id}/classes`} variant="primary" size="lg" key={s.id} className="btn btn-primary btn-lg px-5">{s.name}</Link>)}
                        </div>
                    </div>
                </div>

                <div className="d-flex justify-content-center mb-5">
                    <Button variant="outline-secondary" onClick={() => setPage(page - 1)} disabled={page === 1}>
                        Trước
                    </Button>
                    <span className="mx-3 align-self-center">{page}</span>
                    <Button variant="outline-secondary" onClick={() => setPage(page + 1)} disabled={semesters.length < PAGE_SIZE}>
                        Sau
                    </Button>
                </div>
            </>}

        </>
    );
}

export default Home;