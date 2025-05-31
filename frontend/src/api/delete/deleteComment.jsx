import axios from 'axios';
import { DATA } from "@/app/data";

export default async function deleteComment(commentId) {
    try {
        const response = await axios.delete(`${DATA.apiURL}/comments/${commentId}`);

        return response.data;
    } catch (error) {
        console.error("Error creating post:", error);
        throw error;
    }
}
