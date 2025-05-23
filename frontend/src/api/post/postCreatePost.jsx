import axios from 'axios';
import { DATA } from "@/app/data"

export default async function createPost(postData) {
    try {
        const bodyData = postData

        console.log("bodyData", bodyData)

        const response = await axios.post(`${DATA.apiURL}/posts`,
            bodyData
        );

        return response.data;
    } catch (error) {
        console.error("Error fetching community stats:", error);
        throw error;
    }
}
