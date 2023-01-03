import React, {useState, useEffect} from 'react'
import ModelViewItem from "./ModalWindow/ModelViewItem"
import axios from 'axios'
import { useNavigate  } from 'react-router-dom';

import { axiosGetResponse } from './connection/Host';

function MainContent() {
    let url = '';
    const navigate = useNavigate();
    const [items, setItems] = useState([]);
    const [scrollPositionY, setScrollPositionY] = useState(0);
    const [scrollStatus, setScrollStatus] = useState({top: false, down: false});
    const [openModalViewItem, setOpenModalViewItem] = useState(false);
    const [viewItem, setViewItem] = useState(0);
    const [currentPage, setCurrentPage] = useState(1);
    const [countItemsInPage, setCountItemsInPage] = useState(9);
    const [countItemsInServer, setCountItemsInServer] = useState(0);
    const [countItemsInServerByPartName, setCountItemsInServerByPartName] = useState(0);
    const [fetching, setFetching] = useState(true);
    const [fetchingCountItems, setFetchingCountItems] = useState(true);
    const [fetchingCountItemsByName, setFetchingCountItemsByName] = useState(false);
    const [nameValue, setSeacrhValue] = useState("");
    const [loaderStatus, setLoaderStatus] = useState(true);
    const [basket, setBasket] = useState([])

    if(!window.location.hash) {
        window.location = window.location + '#loaded';
        window.location.reload();
      }

    const searchByName = (e) => {
        setItems([])
        setSeacrhValue(e.target.value);
        setFetchingCountItemsByName(true);
        setCurrentPage(1);
        setFetching(true)
    }

    useEffect(() => {

        if(fetching) {
           if(nameValue !== "") {
            url = `http://localhost:8080/store/certificate/getCertificatesByPartName?size=${countItemsInPage}&page=${currentPage}&name=${nameValue}`;
            setCurrentPage(currentPage => currentPage + 1);
           } else {
            url = `http://localhost:8080/store/certificate/getAllCertificates?size=${countItemsInPage}&page=${currentPage}`;
            setCurrentPage(currentPage => currentPage + 1);
           }

           axios.get(url)
           .then(response => {
               setItems([...items, ...response.data._embedded.giftCertificateDtoList]);
           })
           .catch(e => {
               if(e.response.status === 404) {
                   navigate("/error-page-404")
               }else {
                   navigate("/error-page-server")
               }
           })
           .finally(() => {
           setFetchingCountItems(true);
           setFetching(false)});
        }
    }, [fetching])

    useEffect(() => {
        document.addEventListener('scroll', scrollHandler)
        return function () {
            document.removeEventListener('scroll', scrollHandler)
        }
    }, []);

    const scrollHandler = (e) => {
        //e.target.documentElement.scrollHeight - (e.target.documentElement.scrollTop + window.innerHeight) < 100
        if(e.target.documentElement.scrollHeight - (e.target.documentElement.scrollTop + window.innerHeight) < 100) {
            setFetching(true);
        }

        setScrollStatus({...scrollStatus, top: true, down: true})
    }

    const scrollTop = () => {
        setScrollPositionY(window.scrollY)
        window.scrollTo(0,0);
    }

    const scrollBack = () => {
        window.scrollTo(0, scrollPositionY)

    }

    const acctionViewItem = (e) => {
        setViewItem(e.target.alt);
        setOpenModalViewItem(true);
    }

    useEffect(() => {
        axios.get('http://localhost:8080/store/certificate/getCountCertificates')
        .then(response => {
            setCountItemsInServer(response.data);
        })
        .catch(e => {
            if(e.response.status === 404) {
                navigate("/error-page-404")
            }else {
                navigate("/error-page-server")
            }
        })
        .finally(() => {
            setFetchingCountItems(false);
        })

        if((currentPage * countItemsInPage) < countItemsInServer) {
            setLoaderStatus(true);
        } else {
            setLoaderStatus(false);
        }

    }, [fetchingCountItems]);

    useEffect(() => {
        if(nameValue != "") {
            setFetchingCountItems(true)
            axios.get(`http://localhost:8080/store/certificate/getCountCertificatesByPartName?size=${countItemsInServer}&name=${nameValue}&page=1`)
                .then(response => {
                    setCountItemsInServerByPartName(response.data);
                })
                .catch(e => {
                    if(e.response.status === 404) {
                        navigate("/error-page-404")
                    }else {
                        navigate("/error-page-server")
                    }
                })
                .finally(() => {
                    setFetchingCountItemsByName(false);
                })

        if((currentPage * countItemsInPage) < countItemsInServerByPartName) {
            setLoaderStatus(true);
        } else {
            setLoaderStatus(false);
        }
        }

    }, [fetchingCountItemsByName]);


    const headers = {
        'Content-Type': 'application/json',
        "Access-Control-Allow-Origin": "*",
        'Access-Control-Allow-Credentials':true,
        'Authorization': localStorage.getItem("access_token")
    };

    const addBasket = (e) => {
        setBasket([...basket, e.target.value]);
    }

    useEffect(() => {
        if(basket.length > 0) {
            localStorage.setItem("basket", JSON.stringify(basket));
        }
    }, [basket])

  return (
    <div>
        <div className='container-main-page'>
            <div >
                <div>
                    {openModalViewItem && <ModelViewItem id={viewItem} closeModal={setOpenModalViewItem}/>}
                </div>
            <div className='search-function-main-page'>
                <input type='search' placeholder='Enter name' onChange={searchByName}></input>
            </div>
            <div className='container-content-items'>
                {items.map(item =>
                    <div className='item-content' key={item.giftCertificateDtoId}>
                        <button>
                            <img src='https://img.icons8.com/carbon-copy/344/certificate.png' title='Description' alt={item.giftCertificateDtoId} onClick={acctionViewItem}></img>
                        </button>
                        <h3>{item.name}</h3>
                        <h2>Price: {item.price}$</h2>
                        <button className='add-to-cart-btn'  value={item.giftCertificateDtoId} onClick={addBasket}>Add to cart</button>
                    </div>
                    )}
            </div>
        </div>
    </div>
    <div className='loader'>
        {(loaderStatus) ? (
            <img src='https://i.gifer.com/ZZ5H.gif'></img>
        ) : ("")}

    </div>
    <div>
       {(window.scrollY > 200) ? (
            <div className='scrollTop'>
                <img src='https://img.icons8.com/ios-filled/344/long-arrow-up.png' title='Up' class="scrollTop" alt='' onClick={scrollTop}></img>
             </div>
           ) : ("")}
    </div>
    <div>
        {(window.scrollY < 200 && scrollPositionY !== 0) ? (
           <div className='scrollBack'>
                <img src='https://img.icons8.com/ios-filled/344/long-arrow-down.png' title='Down' class="scrollBack" alt='' onClick={scrollBack}></img>
            </div>
           ) : ("")}
    </div>
</div>
  )
}

 export default MainContent