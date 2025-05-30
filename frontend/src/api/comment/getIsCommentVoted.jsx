import axios from 'axios';
import { DATA } from "@/app/data"

export default async function getIsCommentVoted({ userId, commentId }) {

    console.log("userId", userId);
    console.log("commentId", commentId);

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

        console.log("response.data commentvotes get", response.data);

        return response.data;
    } catch (error) {
        console.error("Error fetching getIsCommentVoted:", error);
        throw error;
    }
}