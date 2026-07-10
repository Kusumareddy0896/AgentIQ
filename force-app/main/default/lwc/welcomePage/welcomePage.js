import { LightningElement } from 'lwc';

export default class WelcomePage extends LightningElement {
    handleClick() {
        alert('Welcome to AgentIQ!');
    }
}