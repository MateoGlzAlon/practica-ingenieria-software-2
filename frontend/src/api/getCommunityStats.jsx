import axios from 'axios';
import { DATA } from "@/app/data"

export default async function getCommunityStats() {
    try {
        const response = await axios.get(`${DATA.apiURL}/stats/community`, {
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
