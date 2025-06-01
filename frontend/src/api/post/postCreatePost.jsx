import axios from 'axios';
import { DATA } from "@/app/data";

export default async function createPost(postData) {
    try {
        const response = await axios.post(`${DATA.apiURL}/posts`, postData);

        console.log("Response data create post:", response.data);

        return response.data;
    } catch (error) {
        console.error("Error creating post:", error);
        throw error;
    }
}
