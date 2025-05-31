import axios from 'axios';
import { DATA } from "@/app/data"

export default async function createPostVotes(userId, postId) {
    try {
        const bodyData = {
            userId: userId,
            postId: postId
        }

        const response = await axios.post(`${DATA.apiURL}/postvotes`,
            bodyData
        );

        return response.data;
    } catch (error) {
        console.error("Error fetching community stats:", error);
        throw error;
    }
}
