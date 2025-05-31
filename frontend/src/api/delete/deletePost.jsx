import axios from 'axios';
import { DATA } from "@/app/data";

export default async function deletePost(postId) {
    try {
        const response = await axios.delete(`${DATA.apiURL}/posts/${postId}`);

        return response.data;
    } catch (error) {
        console.error("Error deleting post:", error);
        throw error;
    }
}
