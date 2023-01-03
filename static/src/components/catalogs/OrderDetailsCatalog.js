import React, {useState, useEffect} from 'react'
import { useNavigate  } from 'react-router-dom';
import axios from 'axios';

function OrderDetailsCatalog() {
  const navigate = useNavigate();

  const [loaderStatus, setLoaderStatus] = useState(false);
  const [details, setDetails] = useState([]);
  const [fetching, setFetching] = useState(true);
  const [orderId, setOrderId] = useState((localStorage.getItem("infoOrderId") === null) ? (0) : (localStorage.getItem("infoOrderId")))

  // const [orderId, setOrderId] = useState(0)

  const searchOrder = (e) => {
      if(e.target.value !== "") {
        setOrderId(e.target.value);
      } else {
        setOrderId(0);
      }
      setDetails([]);
      setFetching(true);
  }

  const headers = { 
    'Content-Type': 'application/json',
    'Authorization': localStorage.getItem("access_token")
};

useEffect(() => {

      setLoaderStatus(true);
      axios.get(`http://localhost:8080/store/admin/order-details/get?id=${orderId}`, {headers})
        .then(response => {
          if(response.data._embedded !== undefined) {
              setDetails([... response.data._embedded.orderDetailsDtoList]);
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
}, [fetching])
 

  const actionCertificateInfo = (e) => {
    localStorage.setItem("infoCertificateId", e.target.value);
    navigate("/certificate-catalog");
    
  }

  const actionOrderInfo = (e) => {
    localStorage.setItem("infoOrderId", e.target.value);
    navigate("/order-catalog");
  }

  const actionReset = () => {
    setOrderId(0);
    setDetails([]);
  }

  return (
    <div>
      <div>
          <input type="number" placeholder='Enter order ID ' className='search-order-details-content' onChange={searchOrder} value={orderId} min="1"></input>
          <button onClick={actionReset} className='btn-order-details-reset'>Reset</button>
      </div>
      <div>
        <table className='order-details-content-table'>
          <thead>
            <tr>
              <th>ID</th>
              <th>Order's ID</th>
              <th>Certificate's ID</th>
              <th>Certificate name</th>
              <th>Certificate price</th>
            </tr>
          </thead>
          <tbody>
            {details.map(item => 
              <tr key={item.orderDetailsId}>
                <td className='position-center'>{item.orderDetailsId}</td>
                <td className='position-center'><button className='btn-details-item-info' value={item.orderId} onClick={actionOrderInfo}>{item.orderId}</button></td>
                <td className='position-center'>
                  <button className='btn-details-item-info' onClick={actionCertificateInfo} value={item.certificateId}>{item.certificateId}</button>
                </td>
                <td>{item.certificateName}</td>
                <td className='position-center'>${item.price}</td>
              </tr>
            ) }
          </tbody>
        </table>
      </div>
        <div className='loader'>
            {(loaderStatus) ? (
                <img src='https://i.gifer.com/ZZ5H.gif'></img>  
            ) : ("")}                
        </div>
    </div>
  )
}

export default OrderDetailsCatalog