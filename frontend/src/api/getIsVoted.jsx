import axios from 'axios';
import { DATA } from "@/app/data"

export default async function getIsVoted(userId, postId) {
    try {
        const response = await axios.get(`${DATA.apiURL}/postvotes/check`, {
            params: {
                userId,
                postId
            },
        });

        return response.data;
    } catch (error) {
        console.error("Error checking vote:", error);
        throw error;
    }
}