import React from 'react';
import { useNavigate  } from 'react-router-dom';
import axios from 'axios';


function ModalMessage({message, closeModal, id, status}) {
    const navigate = useNavigate();
    const headers = { 
        "Access-Control-Allow-Origin": "*",
        'Access-Control-Allow-Credentials':true,
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("access_token")
    };

    const deleteItem = () => {
        axios.delete(`http://localhost:8080/store/certificate/delete?id=${id}`, 
            {headers}
            )
        .then(response => {
            if(response.status === 200){
                message(`The certificate with ${id} was deleted successfully!`);
            }
        })
        .catch(error => {
            if(error.response.status === 400) {
                message(`Error while deleting certificate ID:${id}` + error.response.data.message);
            } else if(error.response.status === 403) {
                navigate("/error-page-403")
              } else {
                navigate("/error-page-server")
              }
        })
        .finally(() => {
            status(true);
            closeModal(false);
        })
    }

  return (
    <div className='form-modal-message'>
        <div className='modalBackground'>
        <div className='modalContainer'>
            <button className='titleCloseBtn' onClick={e => closeModal(false)}>X</button>
            <div className='body-model'>
                <h1>"Are You sure You want to delete item with ID: {id}?"</h1>
            </div>
            <div className='footer-model'>
                <button onClick={deleteItem}>Delete</button>
                <button onClick={e => closeModal(false)} id='cancelBtn'>Cancel</button>
            </div>
        </div>
    </div>
    </div>
  )
}

export default ModalMessage
