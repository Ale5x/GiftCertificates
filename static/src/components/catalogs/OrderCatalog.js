import React, {useState, useEffect} from 'react';
import axios from 'axios';
import { useNavigate  } from 'react-router-dom';
import ModalUserInfo from '../ModalWindow/ModalUserInfo';

function OrderCatalog() {
    let url = "";
    const navigate = useNavigate();
    const [orders, setOrders] = useState([]);
    const [searchByUserIDStatus, setSearchByUserIDStatus] = useState(false);
    const [searchByOrderIDStatus, setSearchByOrderIDStatus] = useState(false);
    const [searchId, setSearchId] = useState("");
    const [searchUserId, setSearchUserId] = useState(((localStorage.getItem("infoUserId") === null) ? (0) : (localStorage.getItem("infoUserId"))));
    const [searchOrderId, setSearchOrderId] = useState(((localStorage.getItem("infoOrderId") === null) ? (0) : (localStorage.getItem("infoOrderId"))));
    const [countOrders, setCountOrders] = useState("");
    const [orderId, setOrderId] = useState("");
    const [fetching, setFetching] = useState(true);
    const [loaderStatus, setLoaderStatus] = useState(false);
    const [nextPage, setNextPage] = useState("");
    const [prevPage, setPrevPage] = useState("");
    const [countItems, setCountItems] = useState(((localStorage.getItem("countOrderLocal") === null) ? (10) : (localStorage.getItem("countOrderLocal"))));
    const [currentPage, setCurrentPage] = useState(1);
    const [fetchingCountItems, setFetchingCountItems] = useState(true);
    const [openModalUserInfo, setOpenModalUserInfo] = useState(false);
    const [userInfo, setUserInfo] = useState(0);

    const headers = { 
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("access_token")
    };


    const navigationNextPage = () => {
        localStorage.setItem("urlOrderLocal", nextPage);
        
        let changePage = localStorage.getItem("pageOrderLocal");

        if(searchByOrderIDStatus) {
            changePage = 1;
        } else {
            changePage++;
        }

        setCurrentPage(changePage);
        localStorage.setItem("pageOrderLocal", changePage);
        window.scrollTo(0,0);
        setFetching(true);

        console.log("PAGE ", localStorage.getItem("pageOrderLocal"))
    }

    const navigationPrevPage = () => {
        url = prevPage;
        localStorage.setItem("urlOrderLocal", url);

        let changePage = currentPage;
        changePage--;
        if(changePage  <= 0) {
            changePage = 1;
        }
        if(searchByOrderIDStatus) {
            changePage = 1;
        }
        setCurrentPage(changePage);
        localStorage.setItem("pageOrderLocal", changePage);
        window.scrollTo(0,0);
        setFetching(true);
    }

    const startPage = () => {
        localStorage.setItem("pageOrderLocal", 1);
        setCurrentPage(1);
        localStorage.removeItem("urlOrderLocal");
        window.scrollTo(0,0);
        setFetching(true);
    }

    const lastPage = () => {
        let page = 1;
        while((page * countItems) < countOrders) {
            page++;
        }
        localStorage.setItem("pageOrderLocal", page);
        setCurrentPage(page);
        window.scrollTo(0,0);
        setFetching(true);
    }

    const countItemsSelect = () => {
        const select = document.getElementById("type-count-out");
        sessionStorage.setItem('countOrderItems', select.options[select.selectedIndex].value);
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
        localStorage.setItem("countOrder", countItems);
        localStorage.removeItem("urlOrderLocal");
        localStorage.removeItem("pageOrderLocal");
        setFetchingCountItems(true);

        setFetching(true)
    }

    const orderDetails = (e) => {
        localStorage.setItem("infoOrderId", e.target.value)
        navigate("/order-details-catalog");
    }

    const searchByUser = () => {
        if(searchId != "") {
            setSearchByUserIDStatus(true);
            setSearchByOrderIDStatus(false);
            localStorage.setItem("pageOrderLocal", 1);
            setFetching(true);
        }
    }

    const searchByOrder = () => {
        if(searchId != "") {
            setSearchByUserIDStatus(false);
            setSearchByOrderIDStatus(true);
            setFetching(true);
            localStorage.setItem("pageOrderLocal", 1);
        }
    }

    const resetSearchInfo = () => {
        setSearchId("");
        setSearchByUserIDStatus(false);
        setSearchByOrderIDStatus(false);
        localStorage.setItem("pageOrderLocal", 1);
        setFetching(true);
    }

    const acctionViewUserInfo = (e) => {
        setUserInfo(e.target.value);
        setOpenModalUserInfo(true);
    }

    useEffect(() => {
        if(searchUserId > 0) {
            setSearchId(searchUserId);
            setSearchByOrderIDStatus(false);
            setSearchByUserIDStatus(true);
            localStorage.removeItem("infoUserId");
            setSearchUserId(0);
        } else if(searchOrderId > 0) {
            setSearchId(searchOrderId);
            setSearchByUserIDStatus(false);
            setSearchByOrderIDStatus(true);
            localStorage.removeItem("infoOrderId");
            setSearchOrderId(0);
        } 
    }, [fetching])

    useEffect(() => {
        axios.get("http://localhost:8080/store/order/getCountOrders", {headers})
        .then(response => {
            setCountOrders(response.data);
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
            setFetchingCountItems(false)});
    }, [fetchingCountItems])

    useEffect(() => {

        setLoaderStatus(true);
        setCurrentPage(((localStorage.getItem("pageOrderLocal") === null) ? (1) : (localStorage.getItem("pageOrderLocal"))));
        if(searchByOrderIDStatus) {
            url = `http://localhost:8080/store/order/get?id=${searchId}`;
        } else if (searchByUserIDStatus) {
            url = `http://localhost:8080/store/order/getOrdersByUser?id=${searchId}&size=${countItems}&page=${currentPage}`;
        } else {
            url = `http://localhost:8080/store/order/getAllOrders?size=${countItems}&page=${currentPage}`;
        }
        localStorage.setItem("urlOrderLocal", url);

        axios.get(url, {headers})
            .then(response => {
                if(response.data._embedded === undefined) {
                    if(localStorage.getItem("pageOrderLocal") > 1) {
                        localStorage.setItem("pageOrderLocal", (localStorage.getItem("pageOrderLocal") - 1));
                        setCurrentPage(localStorage.getItem("pageOrderLocal"));
                    } else {
                        setOrders([]);
                        localStorage.setItem("pageOrderLocal", 1);
                        setCurrentPage(localStorage.getItem("pageOrderLocal"));
                    }
                    } else if(searchByUserIDStatus){
                        if(response.data._embedded === undefined) {
                            if(currentPage > 1) {
                                localStorage.setItem("pageOrderLocal", (localStorage.getItem("pageOrderLocal") - 1));
                                setCurrentPage(localStorage.getItem("pageOrderLocal"));
                            } else {
                                setOrders([]);
                                localStorage.setItem("pageOrderLocal", 1);
                                setCurrentPage(localStorage.getItem("pageOrderLocal"));
                            }
                            
                        } else {
                            setOrders([...response.data._embedded.orderDtoList]);
                            setNextPage(response.data._links.Next.href);
                            setPrevPage(response.data._links.Previous.href);
                        }
                    } 
                    if (searchByOrderIDStatus) {
                        setOrders([response.data]);
                    }

                    if(searchByOrderIDStatus === false && searchByUserIDStatus === false) {
                        setOrders([...response.data._embedded.orderDtoList]);
                        setNextPage(response.data._links.Next.href);
                        setPrevPage(response.data._links.Previous.href);
                    }
            })
            .catch(error => {
                console.log("ERRORR", error)
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
    <div className='order-catalog-content'>
        <div className='order-main-content'>
            <div>
                {openModalUserInfo && <ModalUserInfo closeModal={setOpenModalUserInfo} id={userInfo}/>}
            </div>
            <div className='search-order-content'>
                <input type="number" placeholder='Enter ID' min="1" onChange={e=> setSearchId(e.target.value)} value={searchId}></input>
                <button onClick={searchByUser} className='btn-order-serach-by-user'>User</button>
                <button onClick={searchByOrder} className='btn-order-search-by-id'>Order</button>
                <button onClick={resetSearchInfo} className='btn-order-serach-by-user'>Resert</button>
            </div>
            <div>
                <div className='settings-order-content'>
                    <span className='settings-order-span'>
                        Count items output 
                        <select id='type-count-out' onChange={countItemsSelect}>
                        <option selected="selected" value="count-10">10</option>
                        <option value="count-50">50</option>
                        <option value="count-100">100</option>
                        </select>
                    </span>
                </div>
                <table className='order-content-table'>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>User's ID</th>
                            <th>Price</th>
                            <th>Date</th>
                            <th>Details</th>
                        </tr>
                    </thead>
                    <tbody>
                        {orders.map(item => 
                            <tr key={item.orderId}>
                                <td>{item.orderId}</td>
                                <td><button onClick={acctionViewUserInfo} value={item.userId} className="btn-order-user-info">{item.userId}</button></td>
                                <td>${item.cost}</td>
                                <td>{item.purchaseTime}</td>
                                <td>
                                    <button onClick={orderDetails} value={item.orderId} className="btn-order-info">Show</button>
                                </td>
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
    </div>
  )
}

export default OrderCatalog