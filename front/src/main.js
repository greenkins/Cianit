import {createApp} from "vue";
import App from "./App.vue";
import router from "./router";
import './assets/tailwind.css'
import "bootstrap/dist/css/bootstrap.min.css";
import Toast from 'vue-toastification';
import 'vue-toastification/dist/index.css';

const toastOptions = {
    timeout: 3000, // Время показа уведомления
    closeOnClick: true,
    pauseOnFocusLoss: true,
    pauseOnHover: true,
    draggable: true,
    draggablePercent: 0.6,
    showCloseButtonOnHover: false,
    hideProgressBar: true,
    closeButton: 'button',
    icon: true,
    rtl: false,
};

const app = createApp(App);

app.use(router);
app.mount("#app");
app.use(Toast, toastOptions);
