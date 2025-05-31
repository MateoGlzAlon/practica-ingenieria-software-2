import axios from 'axios';
import { DATA } from "@/app/data";

export default async function setClosedComment({ postId, userId, commentId }) {
    try {

        const bodyData = {
            userId: userId,
            postId: postId,
            commentId: commentId
        }

        const response = await axios.post(`${DATA.apiURL}/comments/accept`, 
            bodyData
        );

        return response.data;
    } catch (error) {
        console.error("Error changing accept in comment:", error);
        throw error;
    }
}