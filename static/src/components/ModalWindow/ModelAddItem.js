import React, {useState} from 'react'
import { useNavigate  } from 'react-router-dom';
import axios from 'axios';

function ModelAddItem({closeModal, message, status}) {
    const navigate = useNavigate();
    const [error, setError] = useState("");
    const [details, setDetails] = useState({name: "", tags: [], duration: "", price: "", description: "",});
    const [inputTag, setInputTag] = useState("");

    const headers = { 
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("access_token")
    };

    const addContent = () => {
        axios.post("http://localhost:8080/store/certificate/create", {
                
                    name: details.name,
                    tags: details.tags,
                    price: details.price,
                    duration: details.duration,
                    description: details.description
            }, 
            {headers}
            )
        .then(response => {
            if(response.status === 201){
                message("The certificate was created successfully!");
                closeModal(false);
            }
        })
        .catch(error => {
            if(error.response.status === 400) {
                setError(error.response.data.message);
            } else if(error.response.status === 403) {
                navigate("/error-page-403")
              } else {
                navigate("/error-page-server")
              }
        })
        .finally(() => {
            status(true);
        })
    }

    const addTag = () => {
        if(inputTag !== "") {
          setDetails({tags: [...details.tags, inputTag]})
        } 
        setInputTag("");
      }
      
      const removeNewTag = (name) => {
      
        const updateTags = details.tags.filter(tag => {
          return tag !== name.target.value;
        })
      
        setDetails({tags: updateTags})
      }

  return (
    <div className='modalBackground'>
        <div className='form-modal-add'>
        <div className='modalContainer'>
            <div className='title-color'>
                <p>Add new Certificate</p>
            </div>
            <div className='error-message'>
                <h3>
                    {
                        (error != "") ? (error) : ("")
                    } 
                </h3>
            </div>
            <div className='body-model'>
               <div className='form-group-model'>
                    <b>Name:</b>
                    <br/>
                    <input type="text" name="name" id="name" onChange={e => setDetails({...details, name: e.target.value})} value={details.name}></input>
               </div>
               <div className='form-group-model'>
                    <b>Price: </b>
                    <br/>
                    <input type="number" name="price" id="price" onChange={e => setDetails({...details, price: e.target.value})} value={details.price} min="0.1" max="99999"></input>
               </div>
               <div className='form-group-model'>
                    <b>Tags:</b> {details.tags.map(tag => <button onClick={removeNewTag} value={tag} className='btn-modal-tag'>{tag}</button>)}
                    <br/>
                    <input type="text" name="tags" id="tags" onChange={e => setInputTag(e.target.value)} value={inputTag}></input>
                    <button className='btn-modal-add' onClick={addTag}>Add</button>
               </div>
               <div className='form-group-model'>
                    <b>Duration: </b>
                    <br/>
                    <input type="number" name="duration" id="duration" onChange={e => setDetails({...details, duration: e.target.value})} value={details.duration} min="1" max="365"></input>
               </div>
               <div className='form-group-model'>
                    <b>Description:</b>
                    <br/>
                    <textarea className='text-content-area' type="text" name="description" id="description" onChange={e => setDetails({...details, description: e.target.value})} value={details.description}></textarea>
               </div>
               
            </div>
            <div className='footer-model'>
                <button onClick={addContent}>Add</button>
                <button onClick={() => closeModal(false)} id='cancelBtn'>Close</button>
            </div>
        </div>
        </div>
    </div>
  )
}

export default ModelAddItem

/*
modalBackground
modalContainer
body
*/