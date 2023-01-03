
import axios from 'axios'

export function GetData(url) {

    const headers = { 
        'Content-Type': 'application/json',
        'Authorization': localStorage.getItem("access_token")
    };

  return axios.get(url, {headers});
}
