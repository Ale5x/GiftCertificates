import axios from 'axios';
import React, {useState, useEffect} from 'react'

function UserOrderRoom() {

    const [orders, setOrders] = useState([]);
    const [fetching, setFetching] = useState(true);

    const headers = { 
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("access_token")
    };

    useEffect(() => {
        axios.get('http://localhost:8080/store/order/user?size=10&page=1', {headers})
        .then(response => {
            console.log("response.", response.data._embedded.orderDtoList)
            setOrders(response.data._embedded.orderDtoList);
        })
        .catch(error => {
            console.log("Error", error)
        })
        .finally(() => {
            console.log("FIN")
            setFetching(false);
        })
    }, [fetching])


  return (
    <div>
        <div>
            <div className='order-room-content-title'>
                My Orders
            </div>
            <div className='order-room-content'>
                
                {orders.map(item =>
                    <div key={item.orderId}>
                        <br></br>
                        <label><b>Order ID:</b> {item.orderId}</label>
                        <label><b>Purchase date -</b> {item.purchaseTime}</label>
                        <label><b>Total price:</b> ${item.cost}</label>
                        
                        {item.orderDetails.map(detail => 
                            
                            <div key={detail.orderDetailsId} className='order-room-content-details '>
                                <label><b>Price:</b> ${detail.price}</label>
                                <label><b>Certificate:</b> {detail.certificateName}</label>
                                
                            </div>
                            )}
                            <br></br>
                            <hr></hr>
                    </div>
                    )}
            </div>
        </div>
    </div>
  )
}

export default UserOrderRoom