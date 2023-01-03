import axios from 'axios';
import React, {useState, useEffect } from 'react'
import { useNavigate  } from 'react-router-dom';

function ModelTagInfo({id, closeModal, message, fetching}) {
    const[tag, setTag] = useState({tagId: 0, name: ""});
    const navigate = useNavigate();

    const headers = { 
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("access_token")
    };

    useEffect(() => {
        axios.get(`http://localhost:8080/store/tag/get?id=${id}`, {headers})
        .then(response => {
            setTag({tagId: response.data.tagId,
                    name: response.data.name});
        })
        .catch(e => {
            console.log("ERROR", e)
        })
        .finally(() => {
            fetching(false)
        })
    }, [fetching]);

    const deleteTag = () => {
        axios.delete(`http://localhost:8080/store/tag/delete?id=${id}`, 
            {headers})
        .then(response => {
            if(response.status === 200){
                message(`The Tag with id: ${id} and name: '${tag.name}' was deleted successfully!`);
            }})
        .catch(error => {
            if(error.response.status === 400) {
                message(`Error while deleting tag ID:${id}` + error.response.data.message);
            } else if(error.response.status === 403) {
                navigate("/error-page-403")
            } else {
                navigate("/error-page-server")
            }})
        .finally(() => {
                fetching(true);
                closeModal(false);
        })
    }

  return (
    <div className='modalBackground'>
        <div className='modalContainer'>
            <div className='title-color'>
                Tag information
            </div>
            <div className='body-model'>
                <p>
                    <b>ID:</b> {tag.tagId}
                </p>
                <p>
                    <b> Name - </b> {tag.name}
                </p>
            </div>
            
            <div className='footer-model'>
                <button onClick={() => closeModal(false)}>Close</button>
                <button onClick={deleteTag}>Delete</button>
            </div>
        </div>
        
    </div>
  )
}

export default ModelTagInfo;
