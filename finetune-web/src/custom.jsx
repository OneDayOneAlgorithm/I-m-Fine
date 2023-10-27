import React from 'react';
import myWebPImage from './myWebPImage.webp';

function loadimg_with_card() {
    return (
        <div class="card border-primary mb-3" style="max-width: 18rem;">
            <div class="card-body text-primary">
                <img src={myWebPImage} alt="My WebP Image" />
                <h5 class="card-title">라마</h5>
            </div>
        </div>
    );
}

export default loadimg_with_card;