import axios from "axios";
// import { useNavigate  } from 'react-router-dom';

// const navigate = useNavigate();

const headers = { 
    'Content-Type': 'application/json',
    'Authorization': localStorage.getItem("access_token")
};

export  function  axiosGet(url) {
   return axios.get(url, {headers});
}

// export function axiosPostWithError(url, body) {
// console.log("Starting")
//     axios.post("http://localhost:8080/login", {body}, 
//             {headers})
//             .then(response  => {
//                 console.log("SUCCESS")
//                if(response.status === 200){
//                     localStorage.setItem("access_token", "Bearer " + response.data.access_token)
//                     localStorage.setItem("roles", response.data.roles)
    
//                 }
//             }
//             )
//         .catch((e) => {
//             console.log(e)
//         })
    // return axios.post(url, {body}, {headers});
// }

