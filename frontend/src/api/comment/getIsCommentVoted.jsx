import axios from 'axios';
import { DATA } from "@/app/data"

export default async function getIsCommentVoted({ userId, commentId }) {

    try {
        const response = await axios.get(`${DATA.apiURL}/commentvotes/check`, {
            params: {
                userId: userId,
                commentId: commentId
            },
            headers: {
                'Content-Type': 'application/json',
            },
        });

        return response.data;
    } catch (error) {
        console.error("Error fetching getIsCommentVoted:", error);
        throw error;
    }
}