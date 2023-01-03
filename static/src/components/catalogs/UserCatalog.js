import axios from 'axios';
import React, {useState, useEffect} from 'react'
import { useNavigate  } from 'react-router-dom';
import ModalUserInfo from '../ModalWindow/ModalUserInfo';

function UserCatalog() {
    let url = "";

    const navigate = useNavigate();
    const [users, setUsers] = useState([]);
    const [nameUser, setNameUser] = useState("");
    const [roleUser, setRoleUser] = useState({id: 0, role: ""});
    const [fetching, setFetching] = useState(true);
    const [nextPage, setNextPage] = useState("");
    const [prevPage, setPrevPage] = useState("");
    const [currentPage, setCurrentPage] = useState(1);
    const [message, setMessage] = useState("");
    const [countItems, setCountItems] = useState(((localStorage.getItem("countUserLocal") === null) ? (10) : (localStorage.getItem("countUserLocal"))));
    const [loaderStatus, setLoaderStatus] = useState(false);
    const [fetchingCountUsers, setFetchingCountItems] = useState(true);
    const [countUsers, setCountUsers] = useState(0);
    const [openModalUserInfo, setOpenModalUserInfo] = useState(false);
    const [userInfo, setUserInfo] = useState(0);




    const headers = { 
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("access_token")
    };

    const searchUser = (e) => {
        setNameUser(e.target.value.replace(/ /g, ''));
        localStorage.removeItem("urlUserLocal");
        localStorage.setItem("pageUserLocal", 1);
        setUsers([]);
        setFetching(true);
    }

    const countItemsSelect = (e) => {
        console.log("countItemsSelect", e.target.value);
        const select = document.getElementById("type-count-out");
        sessionStorage.setItem('countUsers', select.options[select.selectedIndex].value);
        switch(select.options[select.selectedIndex].value) {
            case "count-10":
                // localStorage.setItem("countCertificateLocal", 10);
                setCountItems(10);
                break;
            case "count-50":
                // localStorage.setItem("countCertificateLocal", 50);
                setCountItems(50);
                break;
            case "count-100":
                // localStorage.setItem("countCertificateLocal", 100);
                setCountItems(100);
                break;
            default:
                setCountItems(10);
        }
        localStorage.setItem("countUserLocal", countItems);
        localStorage.removeItem("urlUserLocal");
        localStorage.removeItem("pageUserLocal");
        setFetchingCountItems(true);
        // setFetchingCountItemsByName(true);

        setFetching(true)
    }

    const navigationNextPage = () => {
        localStorage.setItem("urlUserLocal", nextPage);
        let changePage = currentPage;
        changePage++;
        setCurrentPage(changePage);
        localStorage.setItem("pageUserLocal", changePage);
        window.scrollTo(0,0);
        setFetching(true);
    }

    const navigationPrevPage = () => {
        url = prevPage;
        localStorage.setItem("urlUserLocal", url);

        let changePage = currentPage;
        changePage--;
        if(changePage  <= 0) {
            changePage = 1;
        }
        setCurrentPage(changePage);
        localStorage.setItem("pageUserLocal", changePage);
        window.scrollTo(0,0);
        setFetching(true);

    }

    const startPage = () => {
        localStorage.setItem("pageUserLocal", 1);
        setCurrentPage(1);
        localStorage.removeItem("urlUserLocal");
        window.scrollTo(0,0);
        setFetching(true);
    }

    const lastPage = () => {
        let page = 1;
        while((page * countItems) <= countUsers) {
            page++; 
        }

        localStorage.setItem("pageUserLocal", page);
        setCurrentPage(page);
        window.scrollTo(0,0);
        setFetching(true);
    }

    const setRole = (e) => {
        axios.post(`http://localhost:8080/store/user/changeRole`, 
            {roles: [e.target.name], 
             userId: e.target.value},
            {headers})
        .then(response => {
           if(response.status == 200) {
            setMessage("User's role was changed... User id: " + e.target.value);
           }
        })
        .catch(error => {
            if(error.response.status === 404) {
                navigate("/error-page-404")
            } else if(error.response.status === 403) {
              navigate("/error-page-403")
            } else {
              navigate("/error-page-server")
            }
            })
        .finally(() => {
            setLoaderStatus(false);
            setFetching(true)});
    }

    const setStatus = (e) => {

        axios.post(`http://localhost:8080/store/user/changeStatus`,
            {status: e.target.name, 
             userId: e.target.value},
            {headers})
        .then(response => {
           if(response.status === 200) {
            setMessage("User's status was changed... User id: " + e.target.value);
           }
           console.log("RESPONSE", response)
        })
        .catch(error => {
            console.log("ERROR", error)
            if(error.response.status === 404) {
                navigate("/error-page-404")
            } else if(error.response.status === 403) {
              navigate("/error-page-403")
            } else {
              navigate("/error-page-server")
            }
            })
        .finally(() => {
        setLoaderStatus(false);
        setFetching(true)});
   
    }

    const acctionViewItem = (e) => {
        setUserInfo(e.target.value);
        setOpenModalUserInfo(true);
    }

    useEffect(() => {
        url = `http://localhost:8080/store/user/getCountUser`;
        axios.get(url, {headers})
        .then(response => {
            setCountUsers(response.data);
        })
        .catch(error => {
            console.log("ERROR while getting count users", error)
            if(error.response.status === 404) {
                navigate("/error-page-404")
            } else if(error.response.status === 403) {
              navigate("/error-page-403")
            } else {
              navigate("/error-page-server")
            }
            })
        .finally(() => {
        setFetching(false);
        })
    }, [fetching])

    useEffect(() => {
        //Next 2 rows for fix the page
        // localStorage.removeItem("pageUserLocal");
        // localStorage.removeItem("urlUserLocal");

        setLoaderStatus(true);
        setCurrentPage(((localStorage.getItem("pageUserLocal") === null) ? (1) : (localStorage.getItem("pageUserLocal"))));
        
        if(localStorage.getItem("urlUserLocal") !== null) {
            if(nameUser !== "") {
                url = `http://localhost:8080/store/user/getUsersByName?name=${nameUser}&page=${currentPage}&size=${countItems}`;
                localStorage.setItem("urlUserLocal", url);
            } else {
                url = `http://localhost:8080/store/user/getAllUsers?size=${countItems}&page=${currentPage}`;
                localStorage.setItem("urlUserLocal", url);
            }
        } else {
            url = `http://localhost:8080/store/user/getAllUsers?size=${countItems}&page=1`;
            localStorage.setItem("urlUserLocal", url);
        }

        axios.get(url, {headers})
        .then(response => {
            if(response.data._embedded === undefined) {
                if(currentPage > 0) {
                    if(nameUser === "") {
                        localStorage.setItem("pageUserLocal", (localStorage.getItem("pageUserLocal") - 1));
                        setCurrentPage(localStorage.getItem("pageUserLocal"));
                    } else {
                        console.log("BLOCK ELSE")
                        localStorage.setItem("pageUserLocal", 1);
                        setCurrentPage(localStorage.getItem("pageUserLocal"));
                        setUsers([]);
                    }
                } 
            } else {
                setUsers([...response.data._embedded.userDtoList]);
                setNextPage(response.data._links.Next.href);
                setPrevPage(response.data._links.Previous.href); 
            }
            
        })
        .catch(error => {
            console.log("ERRRO ", error)
            console.log("name ", nameUser)
            if(error.response.status === 404) {
                navigate("/error-page-404")
            } else if(error.response.status === 403) {
              navigate("/error-page-403")
            } else {
              navigate("/error-page-server")
            }
            })
        .finally(() => {
        setLoaderStatus(false);
        setFetching(false)});
    }, [fetching]);
    

  return (
    <div>
        <div>
            {(message != "") ? (
                <div className='message'>
                    <button onClick={() => setMessage("")}>X</button>
                    <h1>{message}</h1>
                </div>
        ) : ("")}
        </div>
        <div>
            <input type="search" placeholder='Enter user name' className="search-user-content" onChange={searchUser}></input>
        </div>
        <div className='count-users-info'>
            Count users in the system - <b>{countUsers}</b>
        </div>
        <div>
            {openModalUserInfo && <ModalUserInfo closeModal={setOpenModalUserInfo} id={userInfo}/>}
        </div>
        <div className='settings-user-content'>
            <span className='settings-user-span'>
                    Count items output 
                    <select id='type-count-out' onChange={countItemsSelect}>
                    <option selected="selected" value="count-10">10</option>
                    <option value="count-50">50</option>
                    <option value="count-100">100</option>
                    </select>
                </span>
        </div>
        <div>
            <table className='user-content-table'>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Change Role</th>
                        <th>Status</th>
                        <th>Change status</th>
                        <th>Info/Orders</th>
                    </tr>
                </thead>
                <tbody>
                    {users.map(item => 
                        <tr key={item.userId}>
                            <td>{item.userId}</td>
                            <td>{item.firstName} {item.lastName}</td>
                            <td>{item.email}</td>
                            <td>{item.roles}</td>
                            <td>
                                {(item.roles != "USER") ? (
                                    <button className='btn-user-status' onClick={setRole} value={item.userId} name="user">User</button>
                                ) : ("")}
                                {(item.roles != "ADMIN") ? (
                                    <button className='btn-user-status' onClick={setRole} value={item.userId} name="admin">Admin</button>
                                ) : ("")}
                                
                            </td>
                            <td>{item.status}</td>
                            <td>
                                {(item.status !== "ACTIVE") ? (
                                    <button className='btn-user-status' onClick={setStatus} value={item.userId} name="active">Set Active</button>
                                ) : ("")}

                                {(item.status !== "BLOCKED") ? (
                                    <button className='btn-user-status' onClick={setStatus} value={item.userId} name="blocked">Set Blocked</button>
                                ) : ("")}
                            </td>
                            <td><button className='btn-user-status' onClick={acctionViewItem} value={item.userId}>Info</button></td>
                        </tr>
                    )}
                </tbody>
            </table>
            <div className='loader'>
                {(loaderStatus) ? (
                    <img src='https://i.gifer.com/ZZ5H.gif'></img>  
                ) : ("")}                
            </div>
            <div className='pages'>
                <span>
                    <span className='settings-span'>
                        <button className='btn-navigation' onClick={startPage}>
                            <img src='https://cdn-icons-png.flaticon.com/512/44/44887.png'></img>
                        </button>
                    </span>
                    <span className='settings-span'>
                        <button className='btn-navigation' onClick={navigationPrevPage}>
                            <img src='https://cdn-icons-png.flaticon.com/512/271/271220.png'></img>
                        </button>
                    </span>
                    <span className='current-page'>
                            {currentPage}
                    </span>
                    <span className='settings-span'>
                        <button className='btn-navigation' onClick={navigationNextPage}>
                            <img src='https://cdn-icons-png.flaticon.com/512/271/271228.png'></img>
                        </button>
                    </span>
                    <span className='settings-span'>
                        <button className='btn-navigation' onClick={lastPage}>
                            <img src='https://cdn-icons-png.flaticon.com/512/724/724927.png'></img>
                        </button>
                    </span>
                </span>
            </div>
        </div>
    </div>
  )
}

export default UserCatalog