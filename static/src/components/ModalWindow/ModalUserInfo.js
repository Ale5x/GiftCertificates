import React, {useState, useEffect} from 'react';
import axios from 'axios';
import { useNavigate  } from 'react-router-dom';

function ModalUserInfo({id, closeModal}) {

    const navigate = useNavigate();
    const [user, setUser] = useState({userId: 0,
                                firstName: "",
                                lastName: "",
                                email: "",
                                role:[],
                                status: "",
                                createDate: "",
                                lastUpdateDate: ""});
    const [fetching, setFetching] = useState(true);
    const [errorMessage, setErrorMessage] = useState("");

    const headers = { 
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("access_token")
    };

    const jumpToOrders = () => {
        localStorage.setItem("infoUserId", user.userId)
        navigate("/order-catalog");
    }

    useEffect(() => {
        axios.get(`http://localhost:8080/store/user/get?id=${id}`, {headers})
        .then(response => {
            if(response.status == 200) {
                setUser({
                userId: response.data.userId,
                firstName: response.data.firstName,
                lastName: response.data.lastName,
                email: response.data.email,
                role: [...response.data.roles],
                status: response.data.status,
                lastUpdateDate: response.data.lastUpdateDate,
                createDate: response.data.createDate
              })
            } else {
                console.log("Error while getting info")
            }
        })
        .catch(error => {
          if(error.response.status === 404) {
              navigate("/error-page-404")
          } else if(error.response.status === 403) {
            navigate("/error-page-403")
          }else {
              navigate("/error-page-server")
          }
        })
        .finally(() => 
          setFetching(false));
    }, [fetching]);
 
  return (
    <div className='modalBackground'>
        <div className='modalContainer'>
            <div className='title-color'>
                    <p>Description</p>
            </div>
            <div>
                {errorMessage}
            </div>
            <div className='body-model'>
                <p>
                    <b>ID: </b> <span>{user.userId}</span>
                </p>
                <p>
                    <b>Name: </b> {user.firstName} {user.lastName}
                </p>
                <p>
                    <b>Email:</b> {user.email}
                </p>
                <p>
                    <b>Role:</b> {user.role}
                </p>
                <p>
                    <b>Status:</b> {user.status}
                </p>
                <p>
                    <b>Created:</b> {user.createDate}
                </p>
                <p>
                    <b>Last updated:</b> {user.lastUpdateDate}
                </p>

            </div>
            <div className='footer-model'>
                <button onClick={() => closeModal(false)}>Close</button>
                <button onClick={jumpToOrders}>Orders</button>
            </div>
        </div>
    </div>
  )
}

export default ModalUserInfo