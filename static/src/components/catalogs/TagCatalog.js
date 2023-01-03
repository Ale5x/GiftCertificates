import React, {useState, useEffect} from "react";
// import GetData from "../connection/GetConnection";
import axios from 'axios'
import ModelTagInfo from "../ModalWindow/ModelTagInfo";

import { useNavigate  } from 'react-router-dom';
import ModalTagInfo from "../ModalWindow/ModelTagInfo";

function TagCatalog() {
    let url = "";

    const navigate = useNavigate();
    const [searchTagByName, setSerachTagByName] = useState("");
    const [tagItems, setTagItems] = useState([]);
    const [tag, setTag] = useState({id: 0, name: ""});
    const [nextPage, setNextPage] = useState("");
    const [prevPage, setPrevPage] = useState("");
    const [countViewTags, setCountViewTags] = useState(((localStorage.getItem("countTagLocal") === null) ? (10) : (localStorage.getItem("countTagLocal"))));
    const [fetching, setFetching] = useState(true);
    const [fetchingByname, setFetchingByName] = useState(false);
    const [fetchingById, setFetchingById] = useState(false);
    const [currentPage, setCurrentPage] = useState(1);
    const [viewTagId, setViewTagId] = useState(0);
    const [openModalTagInfo, setOpenModalTagInfo] = useState(false);
    const [createTag, setCreateTag] = useState("");
    const [loaderStatus, setLoaderStatus] = useState(false);
    const [countItemsInServer, setCountItemsInServer] = useState(0);
    const [fetchingCountItems, setFetchingCountItems] = useState(true);
    const [message, setMessage] = useState("");

    const countItemsSelect = () => {
        const select = document.getElementById("type-count-out");
        sessionStorage.setItem("countItems", select.options[select.selectedIndex].value);

        switch(select.options[select.selectedIndex].value) {
            case "count-10":
                localStorage.setItem("countTagLocal", 30);
                console.log("count-10 -> ")
                setCountViewTags(10);
                break;
            case "count-50":
                localStorage.setItem("countTagLocal", 50);
                console.log("count-50 -> ")
                setCountViewTags(50);
                break;
            case "count-100":
                localStorage.setItem("countTagLocal", 100);
      
                setCountViewTags(100);
                break;
            default:
                setCountViewTags(30);
                break;
        }
        localStorage.setItem("countTagLocal", countViewTags);
        localStorage.removeItem("urlTagLocal");
        localStorage.removeItem("pageTagLocal");
        setFetching(true);
        }

        const headers = { 
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem("access_token")
        };

        useEffect(() => {
            setLoaderStatus(true);
            setCurrentPage(((localStorage.getItem("pageTagLocal") === null) ? (1) : (localStorage.getItem("pageTagLocal"))))

            if(searchTagByName !== "") {
                url = `http://localhost:8080/store/tag/getByPartName?name=${searchTagByName}&size=${countViewTags}&page=${currentPage}`;
            } else {
              url = `http://localhost:8080/store/tag/getAllTags?size=${countViewTags}&page=${currentPage}`;  
            }
            localStorage.setItem("urlTagLocal", url);

            axios.get(url, {headers})
                .then(response => {
                    setTagItems([...response.data._embedded.tagDtoList]);
                    setNextPage(response.data._links.Next.href);
                    setPrevPage(response.data._links.Previous.href);
                    setLoaderStatus(false);
                })
                    
                .catch(e => {
                        console.log("ERROR", e)
                        if(e.response.status === 404) {
                            navigate("/error-page-404")
                        } else if(e.response.status === 403) {
                            navigate("/error-page-403")
                        } else {
                            navigate("/error-page-server")
                        }
                    })
                .finally(() => {
                    setFetching(false);
                })
            }, [fetching]);

        const searchByName = (e) => {
            setTagItems([]);
            setSerachTagByName(e.target.value);
            localStorage.removeItem("urlTagLocal");
            localStorage.removeItem("pageTagLocal");
            setFetching(true);
        };

        const acctionViewTag = (e) => {
            setViewTagId(e.target.value);
            setOpenModalTagInfo(true);
        }

        const saveTag = (e) => {
           console.log("TAG -> ", createTag)
            if(createTag !== "") {
                axios.post(`http://localhost:8080/store/tag/create?name`, {
                    name: createTag
                }, {headers})
                .then((response) => {
                    setMessage("Tag created succesfull!")
                })
                .catch(e => {
                    setMessage("The Tag didn't create. Maybe it exists.")
                    console.log("ERROR while save tag", e)
                })
                .finally(() => {
                    setFetching(true);
                }) 
            } 
            
        }

        useEffect(() => {
            axios.get('http://localhost:8080/store/tag/getCountTag', {headers})
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

        const navigationNextPage = () => {
            url = nextPage;
            localStorage.setItem("urlTagLocal", url);
            let changePage = currentPage;
            changePage++;
            setCurrentPage(changePage);
            localStorage.setItem("pageTagLocal", changePage);
            window.scrollTo(0,0);
            setFetching(true);
        }
    
        const navigationPrevPage = () => {
            url = prevPage;
            localStorage.setItem("urlTagLocal", url);
    
            let changePage = currentPage;
            changePage--;
            if(changePage  <= 0) {
                changePage = 1;
            }
            setCurrentPage(changePage);
            localStorage.setItem("pageTagLocal", changePage);
            window.scrollTo(0,0);
            setFetching(true);
        }
    
        const startPage = () => {
            localStorage.setItem("pageTagLocal", 1);
            setCurrentPage(1);
            localStorage.removeItem("urlTagLocal");
            window.scrollTo(0,0);
            setFetching(true);
        }
    
        const lastPage = () => {
            let page = 1;
            if(searchTagByName !== "") {
                // while((page * countViewTags) <= countItemsInServerByPartName) {
                //     page++;
                // }
            } else {
                while((page * countViewTags) <= countItemsInServer) {
                    page++;
                }
            }
            localStorage.setItem("pageTagLocal", page);
            setCurrentPage(page);
            localStorage.removeItem("urlTagLocal");
            window.scrollTo(0,0);
            setFetching(true);
        }

        return(
            <div className="tag-catalog-page">
            {openModalTagInfo && <ModalTagInfo id={viewTagId} closeModal={setOpenModalTagInfo} message={setMessage} fetching={setFetching}></ModalTagInfo>}
            <div className="save-tag">
                <input type="text" placeholder="Enter name" className="input-new-tag-name" onChange={e => setCreateTag(e.target.value)}></input>
                <button onClick={saveTag} className='btn-save-tag'>Save the tag</button>
            </div>
            <div>
            {(message != "") ? (
                <div className='tag-message'>
                    
                    <button onClick={() => setMessage("")}>X</button>
                    <h1>{message}</h1>
                </div>
            ) : ("")}
            </div>
            <div>
                <input type='search' placeholder="Enter name tag" className="search-tag-content" onChange={searchByName}></input>
            </div>
            <div>
            <div className='settings-tag-content'>
                <span className='settings-tag-span'>
                    Count items output 
                    <select id='type-count-out' onChange={countItemsSelect}>
                    <option selected="selected" value="count-10">10</option>
                    <option value="count-50">50</option>
                    <option value="count-100">100</option>
                    </select>
                </span>
            </div>
            <div>
                <div className="tag-container">
                    {tagItems.map(item => 
                    <div className="tag-content">
                            <div key={item.tagId}>
                                <button value={item.tagId} onClick={acctionViewTag} className='btn-tag'>{item.name}</button>
                            </div>
                        </div> 
                    )}
                </div>
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

    export default TagCatalog
