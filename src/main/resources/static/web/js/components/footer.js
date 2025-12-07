Vue.component('footer-component', {
    template: `
        <footer class="d-flex flex-wrap justify-content-evenly align-item-center">
            <div class="footerColor">
                <div class="bloque1 medios-pago d-flex flex-wrap justify-content-around align-items-center">
                    <div style="display: flex; flex-direction: column;">
                        <div style="display: flex; flex-direction: column; align-items: center;">
                            <p class="text-align-center metodos-redes">Atención al Cliente</p>
                            <p class="metodos-redes">4695-2859</p>
                        </div>
                        <div class="medios-pago" style="display: flex; flex-direction: column; align-items: center;">
                            <p class="mb-3 metodos-redes" style="text-align: center;">Métodos De Pago</p>
                            <div class="metodosPago">
                                <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-credit-card" width="44" height="44" viewBox="0 0 24 24" stroke-width="1.5" stroke="#ffffff" fill="none" stroke-linecap="round" stroke-linejoin="round">
                                    <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
                                    <rect x="3" y="5" width="18" height="14" rx="3"/>
                                    <line x1="3" y1="10" x2="21" y2="10"/>
                                    <line x1="7" y1="15" x2="7.01" y2="15"/>
                                    <line x1="11" y1="15" x2="13" y2="15"/>
                                </svg>
                                <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-cash" width="44" height="44" viewBox="0 0 24 24" stroke-width="1.5" stroke="#ffffff" fill="none" stroke-linecap="round" stroke-linejoin="round">
                                    <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
                                    <rect x="7" y="9" width="14" height="10" rx="2"/>
                                    <circle cx="14" cy="14" r="2"/>
                                    <path d="M17 9v-2a2 2 0 0 0 -2 -2h-10a2 2 0 0 0 -2 2v6a2 2 0 0 0 2 2h2"/>
                                </svg>
                            </div>
                        </div>
                    </div>

                    <div class="datosFooter">
                        <div class="horario">
                            <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-calendar-time" width="44" height="44" viewBox="0 0 24 24" stroke-width="1.5" stroke="#ffffff" fill="none" stroke-linecap="round" stroke-linejoin="round">
                                <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
                                <path d="M11.795 21h-6.795a2 2 0 0 1 -2 -2v-12a2 2 0 0 1 2 -2h12a2 2 0 0 1 2 2v4"/>
                                <circle cx="18" cy="18" r="4"/>
                                <path d="M15 3v4"/>
                                <path d="M7 3v4"/>
                                <path d="M3 11h16"/>
                                <path d="M18 16.496v1.504l1 1"/>
                            </svg>
                            <p class="metodos-redes">Lunes a sábados de 10 a 20hs</p>
                        </div>

                        <div class="horario">
                            <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-pin" width="44" height="44" viewBox="0 0 24 24" stroke-width="1.5" stroke="#ffffff" fill="none" stroke-linecap="round" stroke-linejoin="round">
                                <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
                                <path d="M15 4.5l-4 4l-4 1.5l-1.5 1.5l7 7l1.5 -1.5l1.5 -4l4 -4"/>
                                <line x1="9" y1="15" x2="4.5" y2="19.5"/>
                                <line x1="14.5" y1="4" x2="20" y2="9.5"/>
                            </svg>
                            <p class="metodos-redes">Av. De Mayo 2589</p>
                        </div>

                        <div class="horario">
                            <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-mail" width="44" height="44" viewBox="0 0 24 24" stroke-width="1.5" stroke="#ffffff" fill="none" stroke-linecap="round" stroke-linejoin="round">
                                <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
                                <rect x="3" y="5" width="18" height="14" rx="2"/>
                                <polyline points="3 7 12 13 21 7"/>
                            </svg>
                            <a href="mailto:newstyle@gmail.com" class="a-footer">
                                <p class="a-footer">newstyle@gmail.com</p>
                            </a>
                        </div>
                    </div>

                    <div class="text-align-center">
                        <div>
                            <p class="metodos-redes">Redes Sociales</p>
                        </div>
                        <div class="d-flex redes-sociales">
                            <div class="me-2">
                                <a href="https://www.facebook.com/" target="_blank">
                                    <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-brand-facebook" width="44" height="44" viewBox="0 0 24 24" stroke-width="1.5" stroke="#ffffff" fill="none" stroke-linecap="round" stroke-linejoin="round">
                                        <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
                                        <path d="M7 10v4h3v7h4v-7h3l1 -4h-4v-2a1 1 0 0 1 1 -1h3v-4h-3a5 5 0 0 0 -5 5v2h-3"/>
                                    </svg>
                                </a>
                            </div>
                            <div class="me-2">
                                <a href="https://www.instagram.com/" target="_blank">
                                    <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-brand-instagram" width="44" height="44" viewBox="0 0 24 24" stroke-width="1.5" stroke="#ffffff" fill="none" stroke-linecap="round" stroke-linejoin="round">
                                        <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
                                        <rect x="4" y="4" width="16" height="16" rx="4"/>
                                        <circle cx="12" cy="12" r="3"/>
                                        <line x1="16.5" y1="7.5" x2="16.5" y2="7.501"/>
                                    </svg>
                                </a>
                            </div>
                            <div>
                                <a href="https://twitter.com/" target="_blank">
                                    <svg xmlns="http://www.w3.org/2000/svg" class="icon icon-tabler icon-tabler-brand-twitter" width="44" height="44" viewBox="0 0 24 24" stroke-width="1.5" stroke="#ffffff" fill="none" stroke-linecap="round" stroke-linejoin="round">
                                        <path stroke="none" d="M0 0h24v24H0z" fill="none"/>
                                        <path d="M22 4.01c-1 .49 -1.98 .689 -3 .99c-1.121 -1.265 -2.783 -1.335 -4.38 -.737s-2.643 2.06 -2.62 3.737v1c-3.245 .083 -6.135 -1.395 -8 -4c0 0 -4.182 7.433 4 11c-1.872 1.247 -3.739 2.088 -6 2c3.308 1.803 6.913 2.423 10.034 1.517c3.58 -1.04 6.522 -3.723 7.651 -7.742a13.84 13.84 0 0 0 .497 -3.753c-.002 -.249 1.51 -2.772 1.818 -4.013z"/>
                                    </svg>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="bloque2">
                    <div class="line"></div>
                    <div class="footer-final">
                        <div>
                            <p class="parrafo-footer">&copy; New Style 2022 | Todos los derechos reservados</p>
                        </div>
                    </div>
                </div>
            </div>
        </footer>
    `
});