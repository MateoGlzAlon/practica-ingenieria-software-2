import axios from 'axios';
import { DATA } from "@/app/data"

export default async function changeLinksProfile({ userId, github, twitter, website }) {
    try {

        const bodyData = {
            userId: userId,
            github_link: github,
            twitter_link: twitter,
            website_link: website
        }

        //console.log("bodydata = ", bodyData)

        const response = await axios.post(`${DATA.apiURL}/users/change-links`, 
            bodyData
        );

        return response.data;
    } catch (error) {
        console.error("Error fetching community stats:", error);
        throw error;
    }
}