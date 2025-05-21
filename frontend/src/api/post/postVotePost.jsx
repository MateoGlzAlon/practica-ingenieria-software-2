import axios from 'axios';
import { DATA } from "@/app/data"

export default async function getCommunityStats() {
    try {
        const response = await axios.post(`${DATA.apiURL}/stats/community`, {

        });

        return response.data;
    } catch (error) {
        console.error("Error fetching community stats:", error);
        throw error;
    }
}
