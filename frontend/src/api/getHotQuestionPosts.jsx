import axios from 'axios';
import { DATA } from "@/app/data"

export default async function getHotQuestionPosts() {
    try {
        const response = await axios.get(`${DATA.apiURL}/stats/hot-posts`, {
            headers: {
                'Content-Type': 'application/json',
            },
        });

        return response.data;
    } catch (error) {
        console.error("Error fetching community stats:", error);
        throw error;
    }
}