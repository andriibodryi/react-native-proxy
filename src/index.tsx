import { NativeModules } from 'react-native';

type ProxyType = {
  multiply(a: number, b: number): Promise<number>;
};

const { Proxy } = NativeModules;

export default Proxy as ProxyType;
