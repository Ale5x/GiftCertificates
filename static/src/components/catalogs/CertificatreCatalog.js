import React, {useState, useEffect}  from 'react'
import axios from 'axios'
import ModalMessage from '../ModalWindow/ModalMessage'
import ModelViewItem from '../ModalWindow/ModelViewItem';
import ModelAddItem from '../ModalWindow/ModelAddItem';
import ModelUpdateItem from '../ModalWindow/ModelUpdateItem';
import { useNavigate  } from 'react-router-dom';




function CertificatreCatalog() {
    let url = "";
    const navigate = useNavigate();
    const [items, setItems] = useState([]);
    const [nextPage, setNextPage] = useState("");
    const [prevPage, setPrevPage] = useState("");
    const [viewItem, setViewItem] = useState(0);
    const [editItem, setEditItem] = useState(0);
    const [openModalDelete, setOpenModalDelete] = useState(false);
    const [openModalViewItem, setOpenModalViewItem] = useState(false);
    const [openModalEditItem, setOpenModalEditItem] = useState(false);
    const [openModalAddItem, setOpenModalAddItem] = useState(false);

    const [loaderStatus, setLoaderStatus] = useState(false);

    const [typeStatus, setTypeStatus] = useState({typeDefault: true, typeSortDate: false, typeReverseSortDate: false})
    const [countItemsInServer, setCountItemsInServer] = useState(0);
    const [countItemsInServerByPartName, setCountItemsInServerByPartName] = useState(0);
    const [searchId, setSearchId] = useState(((localStorage.getItem("infoCertificateId") === null) ? (0) : (localStorage.getItem("infoCertificateId"))))
    const [id, setId] = useState(0);
    const [message, setMessage] = useState("");
    const [countItems, setCountItems] = useState(((localStorage.getItem("countCertificateLocal") === null) ? (10) : (localStorage.getItem("countCertificateLocal"))));
    const [fetching, setFetching] = useState(true);
    const [fetchingCountItems, setFetchingCountItems] = useState(true);
    const [fetchingCountItemsByName, setFetchingCountItemsByName] = useState(false);
    const [nameValue, setSeacrhValue] = useState("");
    const [currentPage, setCurrentPage] = useState(1);

    const headers = { 
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("access_token")
    };

    const typeOutputItems = () => {
        const select = document.getElementById("type-out");
        setCurrentPage(1);
        setItems([]);
        switch(select.options[select.selectedIndex].value) {
            case "certificates":
                localStorage.setItem("typeCertificateLocal", "certificates");
                setTypeStatus({...typeStatus, typeReverseSortDate: false, typeDefault: true, typeSortDate: false});
                break;
            case "date_reverse":
                localStorage.setItem("typeCertificateLocal", "date_reverse");
                setTypeStatus({...typeStatus, typeReverseSortDate: true, typeDefault: false, typeSortDate: false});
                break;
            case "date":
                localStorage.setItem("typeCertificateLocal", "date");
                setTypeStatus({...typeStatus, typeReverseSortDate: false, typeDefault: false, typeSortDate: true});
                break;
            case "name_reverse":
                localStorage.setItem("typeCertificateLocal", "name_reverse");
                setTypeStatus({...typeStatus, typeReverseSortDate: true, typeDefault: false, typeSortDate: false});
                break;
            case "name":
                localStorage.setItem("typeCertificateLocal", "name");
                setTypeStatus({...typeStatus, typeReverseSortDate: false, typeDefault: false, typeSortDate: true});
                break;
            default:
                localStorage.setItem("typeCertificateLocal", "certificates");
                setTypeStatus({...typeStatus, typeReverseSortDate: false, typeDefault: true, typeSortDate: false});
                break;
        }
        localStorage.removeItem("urlCertificateLocal");
        localStorage.removeItem("pageCertificateLocal")
        setFetching(true)
    }

    const countItemsSelect = () => {
        const select = document.getElementById("type-count-out");
        sessionStorage.setItem('countItems', select.options[select.selectedIndex].value);
        switch(select.options[select.selectedIndex].value) {
            case "count-10":
                // localStorage.setItem("countCertificateLocal", 10);
                setCountItems(10);
                break;
            case "count-50":
                localStorage.setItem("countCertificateLocal", 50);
                setCountItems(50);
                break;
            case "count-100":
                // localStorage.setItem("countCertificateLocal", 100);
                setCountItems(100);
                break;
            default:
                setCountItems(10);
        }
        localStorage.setItem("countCertificateLocal", countItems);
        localStorage.removeItem("urlCertificateLocal");
        localStorage.removeItem("pageCertificateLocal");
        setFetchingCountItems(true);
        setFetchingCountItemsByName(true);

        setFetching(true)
    }


    const searchByName = (e) => {
        setItems([])
        setSeacrhValue(e.target.value.replace(/ /g, ''));
        localStorage.removeItem("urlCertificateLocal");
        localStorage.setItem("pageCertificateLocal", 1);
        setFetching(true)
    }

    const acctionDelete = (e) =>  {
        setId(e.target.value)
        setOpenModalDelete(true)
        setFetching(true);
    }

    const acctionViewItem = (e) => {
        setViewItem(e.target.value);
        setOpenModalViewItem(true);
    }

    const acctionEditItem = (e) => {
        setEditItem(e.target.value);
        setOpenModalEditItem(true)
    }

    const actionReset = () => {
        setSeacrhValue("");
        setSearchId(0);
        localStorage.removeItem("infoCertificateId");
        localStorage.removeItem("urlCertificateLocal");
        setFetching(true);
    }

    const navigationNextPage = () => {
        url = nextPage;
        localStorage.setItem("urlCertificateLocal", url);

        let changePage = currentPage;
        changePage++;
        setCurrentPage(changePage);
        localStorage.setItem("pageCertificateLocal", changePage);
        window.scrollTo(0,0);
        setFetching(true);
    }

    const navigationPrevPage = () => {
        url = prevPage;
        localStorage.setItem("urlCertificateLocal", url);

        let changePage = currentPage;
        changePage--;
        if(changePage  <= 0) {
            changePage = 1;
        }
        setCurrentPage(changePage);
        localStorage.setItem("pageCertificateLocal", changePage);
        window.scrollTo(0,0);
        setFetching(true);
    }

    const startPage = () => {
        localStorage.setItem("pageCertificateLocal", 1);
        setCurrentPage(1);
        localStorage.removeItem("urlCertificateLocal");
        window.scrollTo(0,0);
        setFetching(true);
    }

    const lastPage = () => {
        let page = 1;
        if(nameValue !== "") {
            while((page * countItems) <= countItemsInServerByPartName) {
                page++;
            }
        } else {
            while((page * countItems) <= countItemsInServer) {
                page++;
            }
        }
        localStorage.setItem("pageCertificateLocal", page);
        setCurrentPage(page);
        localStorage.removeItem("urlCertificateLocal");
        window.scrollTo(0,0);
        setFetching(true);
    }

    useEffect(() => {
        if(fetching) {
            setLoaderStatus(true);
            setCurrentPage(((localStorage.getItem("pageCertificateLocal") === null) ? (1) : (localStorage.getItem("pageCertificateLocal"))))
            if(searchId > 0) {
                url = `http://localhost:8080/store/certificate/get?id=${searchId}`;
            } else if(localStorage.getItem("urlCertificateLocal") === null || localStorage.getItem("urlCertificateLocal") === "") {
                if(nameValue !== "") {
                    url = `http://localhost:8080/store/certificate/getCertificatesByPartName?size=${countItems}&page=${currentPage}&name=${nameValue}`;
                    localStorage.setItem("urlCertificateLocal", url);
                } else if(localStorage.getItem("typeCertificateLocal") === "date") {
                    url = `http://localhost:8080/store/certificate/allSortDate?size=${countItems}&page=${currentPage}`;
                    localStorage.setItem("urlCertificateLocal", url);
                } else if(localStorage.getItem("typeCertificateLocal") === "date_reverse") {
                    url = `http://localhost:8080/store/certificate/allSortReverseDate?size=${countItems}&page=${currentPage}`;
                    localStorage.setItem("urlCertificateLocal", url);
                } else if(localStorage.getItem("typeCertificateLocal") === "name") {
                    url = `http://localhost:8080/store/certificate/allSortName?size=${countItems}&page=${currentPage}`;
                    localStorage.setItem("urlCertificateLocal", url);
                }  else if(localStorage.getItem("typeCertificateLocal") === "name_reverse") {
                    url = `http://localhost:8080/store/certificate/allSortReverseName?size=${countItems}&page=${currentPage}`;
                    localStorage.setItem("urlCertificateLocal", url);
                } else {
                    url = `http://localhost:8080/store/certificate/getAllCertificates?size=${countItems}&page=${currentPage}`;
                    localStorage.setItem("urlCertificateLocal", url);
                }
                localStorage.setItem("urlCertificateLocal", url);
            } else {
                url = localStorage.getItem("urlCertificateLocal");
            }

            localStorage.setItem("urlCertificateLocal", url);
            axios.get(url, {headers})
            .then(response => {
                if(response.data._embedded === undefined) {
                   if(currentPage > 1) {
                    localStorage.setItem("pageCertificateLocal", (localStorage.getItem("pageCertificateLocal") - 1));
                    setCurrentPage(localStorage.getItem("pageCertificateLocal"));
                   } else {
                    localStorage.setItem("pageCertificateLocal", 1);
                    setCurrentPage(localStorage.getItem("pageCertificateLocal"));
                   }
                }
                if(searchId > 0) {
                    setItems([response.data]);
                } else {
                    setItems([...response.data._embedded.giftCertificateDtoList]);
                    setNextPage(response.data._links.Next.href);
                    setPrevPage(response.data._links.Previous.href);
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
            setFetching(false)});
        }
    }, [fetching])

    useEffect(() => {
        if(nameValue != "") {
            setFetchingCountItems(true)
            axios.get(`http://localhost:8080/store/certificate/getCountCertificatesByPartName?size=${countItemsInServer}&name=${nameValue}&page=1`, {headers})
                .then(response => {
                    setCountItemsInServerByPartName(response.data);
                })
                .catch(e => {
                    if(e.response.status === 404) {
                        navigate("/error-page-404")
                    } else if(e.response.status === 403) {
                        navigate("/error-page-403")
                    } else {
                        navigate("/error-page-server")
                    }
                })
                .finally(() => {
                    setFetchingCountItemsByName(false);
                })
        }
        
    }, [fetchingCountItemsByName]);

    useEffect(() => {
        axios.get('http://localhost:8080/store/certificate/getCountCertificates', {headers})
        .then(response => {
            setCountItemsInServer(response.data);
        })
        .catch(e => {
            if(e.response.status === 404) {
                navigate("/error-page-404")
            } else if(e.response.status === 403) {
                navigate("/error-page-403")
            } else {
                navigate("/error-page-server")
            }
        })
        .finally(() => {
            setFetchingCountItems(false);
        })
    }, [fetchingCountItems]);

  return (
    <div className='certificate-catalog-content'>
        <div className='addBtn'>
            <button onClick={() => setOpenModalAddItem(true)} className='btn-certificate-add'>Add new Certificate</button>
        </div>
        <div className='main-content'>
        <div>
            {openModalDelete && <ModalMessage message = {setMessage} closeModal={setOpenModalDelete} id={id} status={setFetching}/>}
            {openModalViewItem && <ModelViewItem id={viewItem} closeModal={setOpenModalViewItem}/>}
            {openModalEditItem && <ModelUpdateItem id={editItem} closeModal={setOpenModalEditItem} status={setFetching} message={setMessage}/>}
            {openModalAddItem && <ModelAddItem closeModal={setOpenModalAddItem} message={setMessage} status={setFetching}/>}
        </div>
        <div>
        {(message != "") ? (
            <div className='message'>
                
                <button onClick={() => setMessage("")}>X</button>
                <h1>{message}</h1>
            </div>
        ) : ("")}
        </div>
        
        <div className='search-content'>
            <input type='search' placeholder='Enter name' onChange={searchByName} className='input-search' value={nameValue}></input>
            <button className='btn-certificate-reset' onClick={actionReset}>Reset</button>
        </div>
        <div className='settings-content'>
            <div>
                <span className='settings-span'>
                    Type output 
                    <select id='type-out' onChange={typeOutputItems}>
                        <option selected="selected" value="certificates">Show Certificates</option>
                        <option value="date">Show by date</option>
                        <option value="date_reverse">Show by reverse date</option>
                        <option value="name">Show by name</option>
                        <option value="name_reverse">Show by reverse name</option>
                    </select>
                </span>
                <span className='settings-span'>
                    Count items output 
                    <select id='type-count-out' onChange={countItemsSelect}>
                    <option selected="selected" value="count-10">10</option>
                    <option value="count-50">50</option>
                    <option value="count-100">100</option>
                    </select>
                </span>
            </div>
        </div>
        <div>
            <table className='certificate-content-table'>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Date created</th>
                        <th>Date Updated</th>
                        <th>Price</th>
                        <th>Duration</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {items.map(item => 
                        <tr key={item.giftCertificateDtoId}>
                            <td>{item.giftCertificateDtoId}</td>
                            <td>{item.name}</td>
                            <td>{item.createDate}</td>
                            <td>{item.lastUpdateDate}</td>
                            <td>{item.price}</td>
                            <td>{item.duration}</td>
                            <td>
                                <button onClick={acctionDelete} value={item.giftCertificateDtoId} className='btn-certificate-delete'>Delete</button>
                                <button onClick={acctionEditItem} value={item.giftCertificateDtoId} className='btn-certificate-edit'>Edit</button>
                                <button onClick={acctionViewItem} value={item.giftCertificateDtoId} className='btn-certificate-view'>View</button>
                            </td>
                        </tr>)}
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
    </div>
  )
}

export default CertificatreCatalog