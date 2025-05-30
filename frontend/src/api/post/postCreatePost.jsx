import axios from 'axios';
import { DATA } from "@/app/data";

export default async function createPost(postData) {
    try {
        console.log("Post data:", postData);

        const response = await axios.post(`${DATA.apiURL}/posts`, postData);

        console.log("Response from /posts:", response.data);
        return response.data;
    } catch (error) {
        console.error("Error creating post:", error);
        throw error;
    }
}
